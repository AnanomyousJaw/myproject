package com.emc.demo

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import com.datastax.spark.connector.UDTValue
import org.apache.spark.sql.types._
import com.datastax.driver.core.Session
import com.datastax.spark.connector.writer.WriteConf
import org.apache.spark.sql.SQLContext

object WritingDataToCassandra {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("WritingDataToCassandra").setMaster("local")
      .set("spark.cassandra.connection.host", "127.0.0.1")
    val sparkContext = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sparkContext)
    val empobj = emp("raja", "hyd")
    val emprdd = sparkContext.parallelize(Seq(empobj))
    /*CREATE TABLE cassandrademo(
   name text PRIMARY KEY,
   address text,
   
   );*/
    emprdd.saveToCassandra("tutorialspoint", "cassandrademo", SomeColumns("name", "address"))

  }
  case class emp(name: String, address: String)
}