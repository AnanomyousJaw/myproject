json_obj1 = LOAD 'data.json' 
    USING JsonLoader('food:chararray, person:chararray, amount:int');
B = FOREACH json_obj1 GENERATE person,food,amount;
dump B;
