A = LOAD 'gchat' as (line:chararray);
B = FOREACH A generate FLATTEN(TOKENIZE(line)) as word;
C = GROUP B by word;
D = FOREACH C generate COUNT(B),group;
E = ORDER D by $1;
STORE E into 'chatresult';