function [c,ww] = smooth_simplify(y,span)
      
        robust = 0;
        method = 'lowess';
        x = (1:length(y))';
        t = length(y);
        ok = ~isnan(x);
        idx = 1:t;
        c = zeros(1,t);
        c(ok) = lowess(x(ok),y(ok),span, method,robust);
        c(idx) = c;
        c = c';
        ww = 0;
        
        

end

function c = lowess(x,y, span, method, robust)

n = length(y);
span = floor(span);
span = min(span,n);
c = y;
if span == 1
    return;
end

useLoess = false;
if isequal(method,'loess')
    useLoess = true;
end

diffx = diff(x);

% For problems where x is uniform, there's a faster way
isuniform = uniformx(diffx,x,y);
if isuniform
    % For uniform data, an even span actually covers an odd number of
    % points.  For example, the four closest points to 5 in the
    % sequence 1:10 are {3,4,5,6}, but 7 is as close as 3.
    % Therefore force an odd span.
    span = 2*floor(span/2) + 1;

    c = unifloess(y,span,useLoess);
    if ~robust || span<=2
        return;
    end
end

ynan = isnan(y);
seps = sqrt(eps);
theDiffs = [1; diffx; 1];

    % Compute the non-robust smooth for non-uniform x
    for i=1:n
        % if x(i) and x(i-1) are equal we just use the old value.
        if theDiffs(i) == 0
            c(i) = c(i-1);
            continue;
        end
        
        % Find nearest neighbours
        idx = iKNearestNeighbours( span, i, x, ~ynan );
        
        if isempty(idx)
            c(i) = NaN;
            continue
        end

        x1 = x(idx)-x(i); % center around current point to improve conditioning
        d1 = abs(x1);
        y1 = y(idx);

        weight = iTricubeWeights( d1 );
        if all(weight<seps)
            weight(:) = 1;    % if all weights are 0, just skip weighting
        end

        v = [ones(size(x1)) x1];

        
        v = weight(:,ones(1,size(v,2))).*v;
        y1 = weight.*y1;
        if size(v,1)==size(v,2)
            % Square v may give infs in the \ solution, so force least squares
            b = [v;zeros(1,size(v,2))]\[y1;0];
        else
            b = v\y1;
        end
        c(i) = b(1);
    end


% now that we have a non-robust fit, we can compute the residual and do
% the robust fit if required
end

function ys = unifloess(y,span,useLoess)
%UNIFLOESS Apply loess on uniformly spaced X values

y = y(:);

% Omit points at the extremes, which have zero weight
halfw = (span-1)/2;              % halfwidth of entire span
d = abs((1-halfw:halfw-1));      % distances to pts with nonzero weight
dmax = halfw;                    % max distance for tri-cubic weight

% Set up weighted Vandermonde matrix using equally spaced X values
x1 = (2:span-1)-(halfw+1);
weight = (1 - (d/dmax).^3).^1.5; % tri-cubic weight
v = [ones(length(x1),1) x1(:)];
if useLoess
    v = [v x1(:).^2];
end
V = v .* repmat(weight',1,size(v,2));

% Do QR decomposition
[Q,~] = qr(V,0);

% The projection matrix is Q*Q'.  We want to project onto the middle
% point, so we can take just one row of the first factor.
alpha = Q(halfw,:)*Q';

% This alpha defines the linear combination of the weighted y values that
% yields the desired smooth values.  Incorporate the weights into the
% coefficients of the linear combination, then apply filter.
alpha = alpha .* weight;
ys = filter(alpha,1,y);

% We need to slide the values into the center of the array.
ys(halfw+1:end-halfw) = ys(span-1:end-1);

% Now we have taken care of everything except the end effects.  Loop over
% the points where we don't have a complete span.  Now the Vandermonde
% matrix has span-1 points, because only 1 has zero weight.
x1 = 1:span-1;
v = [ones(length(x1),1) x1(:)];
if useLoess
    v = [v x1(:).^2];
end
for j=1:halfw
    % Compute weights based on deviations from the jth point,
    % then compute weights and apply them as above.
    d = abs((1:span-1) - j);
    weight = (1 - (d/(span-j)).^3).^1.5;
    V = v .* repmat(weight(:),1,size(v,2));
    [Q,~] = qr(V,0);
    alpha = Q(j,:)*Q';
    alpha = alpha .* weight;
    ys(j) = alpha * y(1:span-1);

    % These coefficients can be applied to the other end as well
    ys(end+1-j) = alpha * y(end:-1:end-span+2);
end
end

function isuniform = uniformx(diffx,x,y)
%ISUNIFORM True if x is of the form a:b:c

if any(isnan(y)) || any(isnan(x))
    isuniform = false;
else
    isuniform = all(abs(diff(diffx)) <= eps*max(abs([x(1),x(end)])));
end
end

function idx = iKNearestNeighbours( k, i, x, in )
% Find the k points from x(in) closest to x(i)

if nnz( in ) <= k
    % If we have k points or fewer, then return them all
    idx = find( in );
else
    % Find the distance to the k closest point
    d = abs( x - x(i) );
    ds = sort( d(in) );
    dk = ds(k);
    
    % Find all points that are as close as or closer than the k closest point
    close = (d <= dk);
    
    % The required indices are those points that are both close and "in"
    idx = find( close & in );
end
end

function w = iTricubeWeights( d )
% Convert distances into weights using tri-cubic weight function.
% NOTE that this function returns the square-root of the weights.
%
% Protect against divide-by-zero. This can happen if more points than the span
% are coincident.
maxD = max( d );
if maxD > 0
    d = d/max( d );
end
w = (1 - d.^3).^1.5;
end
