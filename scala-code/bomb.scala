import com.typesafe.config.ConfigFactory
import org.apache.spark
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

  object bomb {
    def main(args: Array[String]) {
      val appConf=ConfigFactory.load()
      //val spark = SparkSession.builder.appName("Simple Application").getOrCreate()
      val conf= new SparkConf().
        setAppName("Search count").
        setMaster(appConf.getString("deploynmentMaster"))
      val sc = new SparkContext(conf)
      val logFile = sc.textFile("E:/bomb.txt")// Should be some file on your system
      //val logData = spark.read.textFile(logFile).cache()
      val numAs = logFile.filter(line => line.contains("bomb")).count()
      val numBs = logFile.filter(line => line.contains("b")).count()
      println(s"Lines with a: $numAs, Lines with b: $numBs")

    }
  }


