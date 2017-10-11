fourth_table = LOAD 'data2.json' 
    USING JsonLoader('recipe:chararray, 
                      ingredients: {(name:chararray)}, 
                      inventor: (name:chararray, age:int)');

fourthhresult = FOREACH fourth_table generate recipe,inventor.name,ingredients,inventor.age;
dump fourthhresult;

