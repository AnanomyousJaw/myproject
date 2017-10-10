import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType};
import org.apache.log4j.Logger
object CsvDemo {
  def main(args: Array[String]) {
    // val log = Logger.getLogger(getClass.getName)
   val conf=new SparkConf().setAppName("CsvDemo").setMaster("local")
   val sc=new SparkContext(conf)
   val customSchema = StructType(Array(
    StructField("id", IntegerType, true),
    StructField("name", StringType, true),
    StructField("salary", StringType, true)))
   val sqlContext=new SQLContext(sc);
  val df = sqlContext.read.format("com.databricks.spark.csv")
    .option("header", "true") // Use first line of all files as header
    .schema(customSchema)
    //.option("inferSchema", "true") // Automatically infer data types
    .load("employee.csv")
    df.show();
  val selectedData = df.select("id", "name")
  selectedData.show()
  }
}