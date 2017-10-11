A = LOAD 'gchat' as (line:chararray);
B = FOREACH A generate FLATTEN(TOKENIZE(line)) as word;
B1 = FILTER B by word=="bomb";
C = GROUP B1 by word;
D = FOREACH C generate COUNT(B1),group;
STORE E into 'chatresult';