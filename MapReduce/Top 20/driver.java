import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
public class Driver {
public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
/*
* I have used my local path in windows change the path as per your
* local machine
*/
args = new String[] { "Replace this string with Input Path location for movies data",
"Replace this string with Input Path location for rating data"
"Replace this string with output Path location for the first job",
"Replace this string with output Path location for the second job" };
/* delete the output directory before running the job */
FileUtils.deleteDirectory(new File(args[2]));
FileUtils.deleteDirectory(new File(args[3]));
/* set the hadoop system parameter */
System.setProperty("hadoop.home.dir", "Replace this string with hadoop home directory location");
if (args.length != 4) {
System.err.println("Please specify the input and output path");
System.exit(-1);
}
Configuration conf = ConfigurationFactory.getInstance();
Job sampleJob = Job.getInstance(conf);
sampleJob.setJarByClass(Driver.class);
sampleJob.getConfiguration().set("mapreduce.output.textoutputformat.separator", "::");
TextOutputFormat.setOutputPath(sampleJob, new Path(args[2]));
sampleJob.setOutputKeyClass(Text.class);
sampleJob.setOutputValueClass(Text.class);
sampleJob.setReducerClass(MoviesRatingJoinReducer.class);
MultipleInputs.addInputPath(sampleJob, new Path(args[0]), TextInputFormat.class, MoviesDataMapper.class);
MultipleInputs.addInputPath(sampleJob, new Path(args[1]), TextInputFormat.class, RatingDataMapper.class);
int code = sampleJob.waitForCompletion(true) ? 0 : 1;
if (code == 0) {
Job job = Job.getInstance(conf);
job.setJarByClass(Driver.class);
job.setJobName("Highest_Viewed");
job.getConfiguration().set("mapreduce.output.textoutputformat.separator", "::");
FileInputFormat.addInputPath(job, new Path(args[2]));
FileOutputFormat.setOutputPath(job, new Path(args[3]));
job.setMapperClass(HighestViewedMoviesMapper.class);
job.setReducerClass(HighestViewMoviesReducer.class);
job.setNumReduceTasks(1);
job.setOutputKeyClass(NullWritable.class);
job.setOutputValueClass(Text.class);
System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}
}