package urlcount;

import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.util.Tool;
import java.io.IOException;


public class urldriver { 

 public static void main(String[] args) throws IOException,ClassNotFoundException, InterruptedException {

        
        Job job=new Job(new Configuration());
	job.setJobName("URL");
        job.setJarByClass(urldriver.class);
        
        job.setInputFormatClass(TextInputFormat.class);
        
        
        job.setMapOutputKeyClass(Text.class);//mapper 3rd argument
        job.setMapOutputValueClass(LongWritable.class);//mapper 4th argument
        

        job.setOutputKeyClass(Text.class);//reducer 3rd argument
        job.setOutputValueClass(IntWritable.class);
        
        job.setMapperClass(urlmapper.class);
       job.setReducerClass(urlreducer.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));//args[0] input
        FileOutputFormat.setOutputPath(job, new Path(args[1]));//args[1] output
        
        job.waitForCompletion(true);
		
    }
}


