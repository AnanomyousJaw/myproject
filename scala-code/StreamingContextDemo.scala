import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StreamingContextDemo {
  def main(args: Array[String]){
   val conf=new SparkConf().setAppName("StreamingContextDemo").setMaster("local")
   val sc=new SparkContext(conf)
   val ssc = new StreamingContext(sc, Seconds(4))
   //C:\\Users\\muppav1\\Desktop\\modulepathscandatafiles
   //val lines = ssc.textFileStream("C:\\Users\\muppav1\\Desktop\\modulepathscandatafiles")
val lines = ssc.textFileStream("inputdir")
 /* val sqlContext= new org.apache.spark.sql.SQLContext(sc)
     val dfs = sqlContext.read.json("employee.json")
     dfs.printSchema()
      dfs.show()*/
val words = lines.flatMap(_.split(" "))
 val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
  wordCounts.print()
  
  ssc.start()
  ssc.awaitTermination();
  }
}