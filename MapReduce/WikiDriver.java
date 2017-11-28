package com.WikiDataMining;



import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class WikiDriver{ 

 public static void main(String[] args) throws IOException,ClassNotFoundException, InterruptedException {

        
        Job job=new Job(new Configuration());
        job.setJobName("WIKI");
        job.setJarByClass(WikiDriver.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        job.setMapperClass(WikiMapper.class);
       job.setReducerClass(WikiReducer.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        job.waitForCompletion(true);
		
    }
    
}