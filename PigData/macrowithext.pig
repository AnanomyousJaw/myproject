IMPORT '/home/sbkt/Desktop/pig-programs/empfilter.macro';
emp = load 'empmacro'using PigStorage(',') as (eno,ename,sal,dno);
empdno15 = empfilter(emp,dno);
dump empdno15;
