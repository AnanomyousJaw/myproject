package com.emc.demo

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkTransformationDemoi {
  def main(args: Array[String]): Unit = {
    val words = Array("one", "two", "two", "three", "three", "three")
    val sparkConf=new SparkConf().setAppName("SparkTransformationDemoi").setMaster("local")
    val sparkContext=new SparkContext(sparkConf)
    val wordPairsRDD = sparkContext.parallelize(words).map(word => (word, 1))
   val wordCountsWithReduce = wordPairsRDD .reduceByKey(_ + _) .collect()
   
  }
}