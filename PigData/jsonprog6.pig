data = LOAD 'data2.json' 
    USING JsonLoader('recipe:chararray, 
                      ingredients: {(name:chararray)}, 
                      inventor: (name:chararray, age:int)');

ordered_data = ORDER data by 'inventor;

fourthhresult = FOREACH ordered_data generate inventor.age,recipe,inventor.name,ingredients;
dump fourthhresult;

