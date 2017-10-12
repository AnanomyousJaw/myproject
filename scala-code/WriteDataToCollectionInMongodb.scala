package com.emc.demo

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import com.mongodb.spark.MongoSpark
import org.bson.Document
import org.apache.spark.sql.SQLContext

object WriteDataToCollectionInMongodb {
  def main(args: Array[String]): Unit = {

    val person1 = Person("Vamsikrishna", "Mupparaju")
    val person2 = Person("Srinadh", "Mupparaju")
    val sparkConf = new SparkConf().setAppName("WriteDataToCollectionInMongodb").setMaster("local")
      .set("spark.mongodb.input.uri", "mongodb://127.0.0.1/sparkmongodb.person")
      .set("spark.mongodb.output.uri", "mongodb://127.0.0.1/sparkmongodb.person")
     val sparkSession = SparkSession.builder()
      .master("local")
      .appName("ReadCollectionDataFromMongoDb")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/sparkmongodb.person")
      .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/sparkmongodb.person")
      .getOrCreate()
    //val sparkContext = new SparkContext(sparkConf)
    val sparkContext=sparkSession.sparkContext
    val sqlContext=new SQLContext(sparkContext)
    //val personrdd = sparkContext.makeRDD(Seq(person1, person2))
    
    val personrdd = sparkContext.parallelize(Seq(person1,person2))
    
    //MongoSpark.save(personrdd)
    //MongoSpark.save(person1.write.option("collection", "hundredClub").mode("overwrite"))
    val personDf =  sqlContext.createDataFrame(personrdd)
MongoSpark.save(personDf)
  }
  
}

// define a case class
case class Person(firstname: String, lastname: String)   