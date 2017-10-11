package com.WordCount;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class WordCountMapper extends Mapper<LongWritable, Text,Text, LongWritable>
{   
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException
    {
            output.write(value, new LongWritable(1));
        } 
     }
