function [q1,q2,q3] = AudioDivisionFunc(Sequence,SMP_Rate)
%UNTITLED 此处显示有关此函数的摘要
%   此处显示详细说明

y_raw = Sequence;

Fs = SMP_Rate;

y_raw_single = (y_raw(:,1) + y_raw(:,2))/2;
%channel separation/mix

[x1,x2] = vad_test(y_raw_single);
x1_real = x1 * 80;
x2_real = x2 * 80;
q1 = x1_real;
q3 = x2_real;
%voice activity detection

y = y_raw;
%just reserved for debugging

clf
%figure is plotted for debugging VAD

single_left = y(:,1);
single_right = y(:,2);
mixed = single_left + single_right;
mixed = mixed/2;
mixed_raw = mixed;
size_vector_raw = size(mixed);
size_scaler_raw = size_vector_raw(1,1);
time_raw = linspace(0,size_scaler_raw,size_scaler_raw);
%channel separation/mix and amplitude normalization

mixed = mixed(x1_real:x2_real,:);
%delete silent samples

Amp_Factor = 10/max(mixed);
mixed = mixed * Amp_Factor;
max(mixed) 
Power = mixed.*mixed;
%calculate power of signal

size_vector = size(mixed);
size_scaler = size_vector(1,1);
time = linspace(0,size_scaler,size_scaler);
comp_env_mixed = hilbert(mixed);
env_mixed = abs(mixed + 1i * comp_env_mixed);
%find power/amplitude envelope by hilbert transformation

downsmp_rate = 8;
%parameter:as is named
graph_gain   = 2;
graph_offset = 10;
%parameters for illustrating
smoothing_init_order = 300;
smoothing_step_order = 10;
smoothing_order = smoothing_init_order;

downsmp = linspace(1,size_scaler,size_scaler/downsmp_rate);
time_dsmp = linspace(1,size_scaler/downsmp_rate,size_scaler/downsmp_rate);
%calculating envelope and peak coordinate by down sampling to get better performance

mixed_downsmp = mixed(floor(downsmp));
env_mixed_downsmp = env_mixed(floor(downsmp));

%env_mixed_dsmp_filtered = smooth(env_mixed_downsmp,smoothing_init_order,'lowess');
env_mixed_dsmp_filtered = smooth_simplify(env_mixed_downsmp,smoothing_init_order);

threshold = 0.05;
for count = 1:size_scaler/downsmp_rate
    if env_mixed_dsmp_filtered(count) < threshold 
       env_mixed_dsmp_filtered(count) = 0;
    end
end

neg_env_mixed_dsmp_filtered = -env_mixed_dsmp_filtered;

[minv,minl] = findpeaks(neg_env_mixed_dsmp_filtered,'minpeakdistance',441);

size_peak_vector = size(minl);
%perform primary smooth filtering 

peaks_num = 1;
while (size_peak_vector(1,1) > peaks_num)&&(smoothing_order <= 1800)
    
smoothing_order = smoothing_order + smoothing_step_order;

if smoothing_order >=8000
can_not_divide = 1;
else
can_not_divide = 0;
end
    
%env_mixed_dsmp_filtered = smooth(env_mixed_downsmp,smoothing_order,'lowess');
env_mixed_dsmp_filtered = smooth_simplify(env_mixed_downsmp,smoothing_order);

threshold = 0.05;
for count = 1:size_scaler/downsmp_rate
    if env_mixed_dsmp_filtered(count) < threshold 
       env_mixed_dsmp_filtered(count) = 0;
    end
end

neg_env_mixed_dsmp_filtered = -env_mixed_dsmp_filtered;

[minv,minl] = findpeaks(neg_env_mixed_dsmp_filtered,'minpeakdistance',441);

size_peak_vector = size([minv,minl]);
    
end
%Keep filtering until number of peaks is reduced to a certain constant 

env_mixed_dsmp_filtered = env_mixed_dsmp_filtered * graph_gain + graph_offset; 
%adjust graphic size to get better understanding

minl_recover = minl * downsmp_rate;
%coordinate recover
    
%hold on
% plot(time_dsmp,env_l_dsmp_filtered);
%plot(time_dsmp * downsmp_rate , env_mixed_dsmp_filtered);
% plot(minl,env_l_dsmp_filtered(minl),'*','color','B');
%plot(minl_recover,env_mixed_dsmp_filtered(minl),'*','color','B');
% plot(time_dsmp,single_left_downsmp);
%plot(time,mixed);
%hold off
%plotting process no longer needed

mixed2play = mixed / Amp_Factor;
%Amplitude normalization for playing


if can_not_divide == 1
   q2 = -1; 
else
   q2 = q1 + minl_recover; 
end    
%get cut point referred to total length

end

