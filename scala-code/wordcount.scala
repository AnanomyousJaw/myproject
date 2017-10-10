import com.typesafe.config._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object wordcount {
  def main(args: Array[String]): Unit = {
    val appConf=ConfigFactory.load()
    val conf= new SparkConf().
      setAppName("word count").
      setMaster(appConf.getString("deploynmentMaster"))
    val sc = new SparkContext(conf)
    val inputPath=args(0)
    val outputPath=args(1)
    val wc=sc.textFile(inputPath).flatMap(line=>line.split(",")).map(rec=>(rec,1)).reduceByKey((acc,value)=>acc + value)
    wc.saveAsTextFile(outputPath)
  }
}
