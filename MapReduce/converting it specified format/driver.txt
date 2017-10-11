import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
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
args = new String[] { "Replace this string with Input Path location for rating data",
"Replace this string with Input Path location for users data",
"Replace this string with output Path location for the first job",
"Replace this string with output Path location for the second job",
"Replace this string with Input Path location for movies data",
"Replace this string with Input mapping file location for profession id and profession",
"Replace this string with output Path location for the third/final job",
};
/* delete the output directory before running the job */
FileUtils.deleteDirectory(new File(args[2]));
/* set the hadoop system parameter */
System.setProperty("hadoop.home.dir", "Replace this string with hadoop home directory location");
if (args.length != 7) {
System.err.println("Please specify the input and output path");
System.exit(-1);
}
Configuration conf = ConfigurationFactory.getInstance();
conf.set("id.to.profession.mapping.file.path", args[5]);
Job sampleJob = Job.getInstance(conf);
sampleJob.setJarByClass(Driver.class);
sampleJob.getConfiguration().set("mapreduce.output.textoutputformat.separator", "::");
TextOutputFormat.setOutputPath(sampleJob, new Path(args[2]));
sampleJob.setOutputKeyClass(Text.class);
sampleJob.setOutputValueClass(Text.class);
sampleJob.setReducerClass(UsersRatingJoinReducer.class);
MultipleInputs.addInputPath(sampleJob, new Path(args[0]), TextInputFormat.class, RatingDataMapper.class);
MultipleInputs.addInputPath(sampleJob, new Path(args[1]), TextInputFormat.class, UsersDataMapper.class);
int code = sampleJob.waitForCompletion(true) ? 0 : 1;
int code2 = 1;
if (code == 0) {
Job sampleJob2 = Job.getInstance(conf);
sampleJob2.setJarByClass(Driver.class);
sampleJob2.getConfiguration().set("mapreduce.output.textoutputformat.separator", "::");
TextOutputFormat.setOutputPath(sampleJob2, new Path(args[3]));
sampleJob2.setOutputKeyClass(Text.class);
sampleJob2.setOutputValueClass(Text.class);
sampleJob2.setReducerClass(MoviesRatingJoinReducer.class);
MultipleInputs.addInputPath(sampleJob2, new Path(args[2]), TextInputFormat.class,
UsersRatingDataMapper.class);
MultipleInputs.addInputPath(sampleJob2, new Path(args[4]), TextInputFormat.class, MoviesDataMapper.class);
code2 = sampleJob2.waitForCompletion(true) ? 0 : 1;
}
if (code2 == 0) {
Job job = Job.getInstance(conf);
job.setJarByClass(Driver.class);
job.getConfiguration().set("mapreduce.output.textoutputformat.separator", "::");
job.setJobName("Find_Highest_Rank");
FileInputFormat.addInputPath(job, new Path(args[3]));
FileOutputFormat.setOutputPath(job, new Path(args[6]));
job.setMapperClass(AggregatedDataMapper.class);
job.setReducerClass(AggregatedReducer.class);
job.setNumReduceTasks(1);
job.setOutputKeyClass(Text.class);
job.setOutputValueClass(Text.class);
System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}
}