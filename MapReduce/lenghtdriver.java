package lenghtcount;

	import org.apache.hadoop.conf.Configuration;
	import org.apache.hadoop.conf.Configured;
	import org.apache.hadoop.fs.Path;
	import org.apache.hadoop.io.IntWritable;
	import org.apache.hadoop.io.LongWritable;
	import org.apache.hadoop.io.Text;
	import org.apache.hadoop.mapreduce.Job;
	import org.apache.hadoop.mapreduce.Reducer;
	import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
	import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
	import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
	import org.apache.hadoop.util.Tool;
	import java.io.IOException;


	public class lenghtdriver{ 

	 public static void main(String[] args) throws IOException,ClassNotFoundException, InterruptedException {

	        
	        Job job=new Job(new Configuration());
		job.setJobName("LENGTH OF WORD");
	        job.setJarByClass(lenghtdriver.class);
	        
	        job.setInputFormatClass(TextInputFormat.class);
	        
	        
	        job.setMapOutputKeyClass(Text.class);
	        job.setMapOutputValueClass(LongWritable.class);
	        

	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(IntWritable.class);
	        
	        job.setMapperClass(lenghtmapper.class);
	       job.setReducerClass(lenghtreducer.class);
	        
	        FileInputFormat.addInputPath(job, new Path(args[0]));
	        FileOutputFormat.setOutputPath(job, new Path(args[1]));
	        
	        job.waitForCompletion(true);
			
	    }
	    
	






}
