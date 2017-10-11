third_table = LOAD 'data2.json' 
    USING JsonLoader('recipe:chararray, 
                      ingredients: {(name:chararray)}, 
                      inventor: (name:chararray, age:int)');

dump third_table;

