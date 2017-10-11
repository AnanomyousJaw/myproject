emp = load 'empmacro' using PigStorage(',') as (eno,ename,sal,dno);
empdno15 =filter emp by $3==15;
dump empdno15;
