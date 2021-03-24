function [q1,q2,q3,y_raw] = test(path)

sample = [1,inf];

[y_raw,Fs] = audioread(path , sample);
%loading audio file
[Q1,Q2,Q3] = AudioDivisionFunc(y_raw,Fs);
q1 = Q1;
q2 = Q2;
q3 = Q3;
y_raw = y_raw;


end
