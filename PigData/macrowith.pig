DEFINE myfilter(objvar,colvar) returns x{
$x = filter $objvar by $colvar==15;
};

emp = load 'empmacro'using PigStorage(',') as (eno,ename,sal,dno);
empdno15 =myfilter( emp,dno);
dump empdno15;
