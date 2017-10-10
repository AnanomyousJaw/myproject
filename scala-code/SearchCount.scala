import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.hadoop.fs._

object SearchCount {
  def main(args: Array[String]): Unit = {
    val appConf=ConfigFactory.load()
    val conf= new SparkConf().
      setAppName("Search count").
      setMaster(appConf.getString("deploynmentMaster"))
    val sc = new SparkContext(conf)
    val inputPath=args(0)
    val outputPath=args(1)
    val word =sc.textFile(inputPath)
    val bombcount=word.flatMap(line => line.split(":"))
    val count=bombcount.flatMap(wor=>wor.split(" ")).map(rec=>(rec,1)).reduceByKey((acc,value)=> acc + value)
    val fil=count.filter(a=>(a._1)=="bomb")
    fil.foreach(println)
    //fil.saveAsTextFile(outputPath)
  }
}
