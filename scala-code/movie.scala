import breeze.linalg.Axis._1
import com.typesafe.config.ConfigFactory
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.B
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import scala.swing.event.Key
object movie {
  def main(args: Array[String]): Unit = {
    val appConf = ConfigFactory.load()
//declearing spark configuration
    val conf = new SparkConf().
      setAppName("Search count").
      setMaster(appConf.getString("deploynmentMaster"))
// declearing the spark context and also giving the parameters
    val sc = new SparkContext(conf)
    val inputPath0 = args(0)
    val outputPath0 = args(1)

//creating RDD by loading the data
    val ratingRDD = sc.textFile(inputPath0 + "/ratings.dat")
    val movieRDD = sc.textFile(inputPath0 + "/movies.dat")
//reguired to split the data according to requirenment of rating data
    val rating= ratingRDD.map(line=>(line.split("::")(1).toInt))
    val ratcount= rating.map(word=>(word,1)).reduceByKey((acc,value)=>acc+value)//counting the accureancy of movie viewed
//required to split the data accordingly of movie data
    val movie= movieRDD.map(rec=>(rec.split("::")(0).toInt,rec.split("::")(1).toString))
//joining the two data that is rating data and movie data
    val moviejoin= movie.join(ratcount)
// changing the order of the joined output according to the requirenment
    val moviemap= moviejoin.map(recc=>(recc._2._2,(recc._2._1,recc._1)))
//sorting the data by key i.e descending order
    val moviesort= moviemap.sortByKey(false)

    val top=moviesort.map(b=>(b._2._1,b._2._2))
    val top10=top.take(10)
    //val topmap=top10.map(c=>(c._1,c._2))
    //topmap.foreach(println)
    val topsort=top10.sortBy(d=>(d._1,d._2))
    topsort.foreach(println)
    //val top101=topsort.take(10)
   val topmap1=topsort.map(e=>(e._1))
    //val tops= topmap1.sortBy(m=>(m._1)) //saving the data in the form of textfile
    topmap1.take(10).foreach(println)
    //tops.saveAsFile(outputPath0+"/")
      val top20= moviemap.map(e=>(e._1,e._2._1,e._2._2))
    //top20.foreach(println)
    val top20fil=top20.filter(f=>(f._1 >= 40))

    val top20so=top20fil.sortBy(k=>(~k._1,k._2,k._3))
    top20so.collect().take(20).foreach(println)
    val topst=top20so.map(l=>(l._2,l._3))
    //val topst1=topst.collect()
    //topst.foreach(println)
    topst.saveAsTextFile(outputPath0)

  }
}
