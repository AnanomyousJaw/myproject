package lenghtcount;





import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class lenghtmapper extends Mapper<LongWritable, Text,Text, LongWritable>
{  
    public void map(LongWritable key, Text value, Context output) throws IOException, InterruptedException
    {int x;
       
   x= value.getLength();
 String s =Integer.toString(x);
        	output.write(new Text(s), new LongWritable(1));    
    } 
     }



