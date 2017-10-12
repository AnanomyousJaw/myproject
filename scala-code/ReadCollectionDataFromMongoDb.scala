package com.emc.demo

import com.mongodb.spark.MongoSpark

object ReadCollectionDataFromMongoDb {
  def main(args: Array[String]): Unit = {

    /* Create the SparkSession.
     * If config arguments are passed from the command line using --conf,
     * parse args for the values to set.
     */
    import org.apache.spark.sql.SparkSession

    val spark = SparkSession.builder()
      .master("local")
      .appName("ReadCollectionDataFromMongoDb")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/sparkmongodb.employee")
      .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/sparkmongodb.employee")
      .getOrCreate()
      val rdd = MongoSpark.load(spark)

println(rdd.count)
rdd.collect().foreach(println)
print("......................")
}
}