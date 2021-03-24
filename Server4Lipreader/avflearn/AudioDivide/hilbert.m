function x = hilbert(xr,n)
%HILBERT  Discrete-time analytic signal via Hilbert transform.
%   X = HILBERT(Xr) computes the so-called discrete-time analytic signal
%   X = Xr + i*Xi such that Xi is the Hilbert transform of real vector Xr.
%   If the input Xr is complex, then only the real part is used: Xr=real(Xr).
%   If Xr is a matrix, then HILBERT operates along the columns of Xr.
%
%   HILBERT(Xr,N) computes the N-point Hilbert transform.  Xr is padded with 
%   zeros if it has less than N points, and truncated if it has more.  
%
%   For a discrete-time analytic signal X, the last half of fft(X) is zero, 
%   and the first (DC) and center (Nyquist) elements of fft(X) are purely real.
%
%   EXAMPLE:
%          Xr = [1 2 3 4];
%          X = hilbert(Xr)
%          % produces X=[1+1i 2-1i 3-1i 4+1i] such that Xi=imag(X)=[1 -1 -1 1] is the
%          % Hilbert transform of Xr, and Xr=real(X)=[1 2 3 4].  Note that the last half
%          % of fft(X)=[10 -4+4i -2 0] is zero (in this example, the last half is just
%          % the last element).  Also note that the DC and Nyquist elements of fft(X)
%          % (10 and -2) are purely real.
%
%   See also FFT, IFFT, ENVELOPE.

%   Copyright 1988-2008 The MathWorks, Inc.

%   References:
%     [1] Alan V. Oppenheim and Ronald W. Schafer, Discrete-Time
%     Signal Processing, 2nd ed., Prentice-Hall, Upper Saddle River, 
%     New Jersey, 1998.
%
%     [2] S. Lawrence Marple, Jr., Computing the discrete-time analytic 
%     signal via FFT, IEEE Transactions on Signal Processing, Vol. 47, 
%     No. 9, September 1999, pp.2600--2603.

%#codegen

narginchk(1,2);
if isempty(coder.target)
    if nargin == 1
        x = hilbert_ml(xr);
    else
        x = hilbert_ml(xr,n);
    end
else
    if nargin == 1
        x = hilbert_cg(xr);
    else
        coder.internal.prefer_const(n);
        x = hilbert_cg(xr,n);
    end
end

%--------------------------------------------------------------------------

function x = hilbert_ml(xr,n)
if nargin<2, n=[]; end
if ~isreal(xr)
  warning(message('signal:hilbert:Ignore'))
  xr = real(xr);
end
% Work along the first nonsingleton dimension
[xr,nshifts] = shiftdim(xr);
if isempty(n)
  n = size(xr,1);
end
x = fft(xr,n,1); % n-point FFT over columns.
h  = zeros(n,~isempty(x)); % nx1 for nonempty. 0x0 for empty.
if n > 0 && 2*fix(n/2) == n
  % even and nonempty
  h([1 n/2+1]) = 1;
  h(2:n/2) = 2;
elseif n>0
  % odd and nonempty
  h(1) = 1;
  h(2:(n+1)/2) = 2;
end
x = ifft(x.*h(:,ones(1,size(x,2))),[],1);

% Convert back to the original shape.
x = shiftdim(x,-nshifts);

%--------------------------------------------------------------------------

function x = hilbert_cg(xr,n)
narginchk(1,2);
if nargin == 2
    coder.internal.prefer_const(n);
end
if ~isreal(xr)
    coder.internal.warning('signal:hilbert:Ignore');
    if nargin == 2
        x = hilbert_cg(real(xr),n);
    else
        x = hilbert_cg(real(xr));
    end
    return
end
% Operate over first non-singleton dimension.
dim = HilbertNonSingletonDim(xr);
if nargin == 1
    x = coder.nullcopy(complex(xr));
    if isvector(xr)
        x(:) = HilbertColumnwise(xr(:));
    elseif dim == 1
        x(:) = HilbertColumnwise(xr);
    else
        % Use reshape rather than shiftdim because the code generation
        % shiftdim ultimately requires a fixed number of shifts.
        ncols = coder.internal.prodsize(xr,'above',dim);
        xrshifted = reshape(xr,size(xr,dim),ncols);
        x(:) = HilbertColumnwise(xrshifted);
    end
else
    sz = size(xr);
    sz(dim) = n;
    x = coder.nullcopy(zeros(sz,'like',complex(xr)));
    if isvector(xr)
        x(:) = HilbertColumnwise(xr(:),n);
    elseif dim == 1
        x(:) = HilbertColumnwise(xr,n);
    else
        % Use reshape rather than shiftdim because the code generation
        % shiftdim ultimately requires a fixed number of shifts.
        ncols = coder.internal.prodsize(xr,'above',dim);
        xrshifted = reshape(xr,size(xr,dim),ncols);
        x(:) = HilbertColumnwise(xrshifted,n);
    end
end

%--------------------------------------------------------------------------

function x = HilbertColumnwise(xr,n)
if nargin == 2
    coder.internal.prefer_const(n);
    x = fft(xr,n,1); % n-point FFT over columns.
else
    n = size(xr,1);
    x = fft(xr,[],1); % n-point FFT over columns.
end
nrows = coder.internal.indexInt(max(0,n));
ncols = coder.internal.prodsize(x,'above',1);
ONE = coder.internal.indexInt(1);
halfn = eml_rshift(nrows,ONE);
if eml_bitand(nrows,ONE) == 0
    lastIndexToDouble = halfn;
else
    lastIndexToDouble = halfn + 1;
end
firstIndexToZero = halfn + 2;
for j = 1:ncols
    for i = 2:lastIndexToDouble
        x(i,j) = x(i,j)*2;
    end
    for i = firstIndexToZero:nrows
        x(i,j) = 0;
    end
end
x = ifft(x,[],1);

%--------------------------------------------------------------------------

function dim = HilbertNonSingletonDim(x)
% Like MATLAB's first nonsingleton dim except for scalars returns 1 instead
% of 2.
ONE = coder.internal.indexInt(1);
dim = ONE;
for k = ONE:eml_ndims(x)
    if size(x,k) ~= 1
        dim = k;
        break
    end
end

%--------------------------------------------------------------------------
