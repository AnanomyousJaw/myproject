package com.wikidatamining1;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class WikiMapper extends Mapper<LongWritable, Text,Text, LongWritable>
{  
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException
    {
        String Line= value.toString(); //for storing into string
        String a[]=Line.split(" ");   // for spli based on space
        if(Long.parseLong(a[3])>40000){
         output.write(new Text(a[1]), new LongWritable(1));
        }        
        } 
     }
