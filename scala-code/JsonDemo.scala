
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
object JsonDemo {
  def main(args: Array[String]) {
     val conf = new SparkConf().setAppName("JsonDemo").setMaster("local")
      val sc = new SparkContext(conf)
     val sqlContext= new org.apache.spark.sql.SQLContext(sc)
     val dfs = sqlContext.read.json("employee.json")
     dfs.printSchema()
      dfs.show()
      //dfs.select("name","age").write.format("parquet").save("Output",SaveMode.ErrorIfExist)
  }
}