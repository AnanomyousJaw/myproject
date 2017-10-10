import org.apache.spark.SparkContext

import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object ParquetExample {
  def main(args: Array[String]): Unit = {
      val conf = new SparkConf().setAppName("ParquetExample").setMaster("local")
      val sc = new SparkContext(conf)
     val sqlContext= new org.apache.spark.sql.SQLContext(sc)
   val data=Seq(Person("Brown", "John", 1969),Person("Lacava", "Alessandro", 1976))
   data.foreach(println)
   val df = sqlContext.createDataFrame(Seq(Person("Brown", "John", 1969),Person("Lacava", "Alessandro", 1976)))
  // df.select("lastname", "firstname","birthYear").write.mode(org.apache.spark.sql.SaveMode.Append).format("parquet").save("outputdir")
   //df.write.format("parquet").mode(org.apache.spark.sql.SaveMode.Append).partitionBy("firstname").saveAsTable("example") 
/* val newDataDF = sqlContext.
                read.parquet("outputdir")        // read back parquet to DF
newDataDF.show()  */
  }
  case class Person(lastname: String, firstname: String, birthYear: Int)
}