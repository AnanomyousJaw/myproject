package com.WikiDataMining;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WikiMapper extends
		Mapper<LongWritable, Text, Text, LongWritable> {

	public void map(LongWritable key, Text value, Context output)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		
		String a[] = line.split(" ");
		
		

	
		
		// wiki program only 40000
		if(Long.parseLong(a[3])>40000){
			output.write(new Text(a[1]), new LongWritable(1));
		}
				
		
		
		
		

	}
}
