package bombpgm;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class bmapper extends Mapper<LongWritable, Text,Text, LongWritable>
{
    
    
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException
    {
    	 String Line= value.toString(); //for storing into string
	        String a[]=Line.split(":");  
	        String b[]=a[1].split(" ");
	        for(int i=0;i<b.length;i++){
		    	   if(b[i].equalsIgnoreCase("bomb")){
		     
         output.write(new Text (a[0]), new LongWritable(1));}}
        
        } 
     }