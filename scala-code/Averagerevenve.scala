import com.typesafe.config.ConfigFactory
import org.apache.spark.{SparkConf, SparkContext}

object Averagerevenve {
  def main(args: Array[String]): Unit = {
    val appConf = ConfigFactory.load()
    //declearing spark configuration
    val conf = new SparkConf().
      setAppName("Average Revenue").
      setMaster(appConf.getString("deploynmentMaster"))
    // declearing the spark context and also giving the parameters
    val sc = new SparkContext(conf)
    val inputPath0 = args(0)
    //val outputPath0 = args(1)
    val order = sc.textFile(inputPath0 + "/orders/part-00000")
    val order_item = sc.textFile(inputPath0 + "/order_items/part-00000")
    val orderfil= order.filter(a=>(a.split(",")(3)=="COMPLETE"))
    val ordermap=orderfil.map(line=>(line.split(",")(0).toInt,line.split(",")(1)))
    val order_itemmap=order_item.map(line=>(line.split(",")(1).toInt,line.split(",")(4).toFloat))
    val orderred=order_itemmap.reduceByKey((acc, value)=>acc + value)
    //val odertry=orderred.filter(recc=>(recc._1)==36)
    val orderjo=ordermap.join(order_itemmap)
    val maporder=orderjo.map(b=>(b._2._1,b._2._2))
    val aggrorder=maporder.aggregateByKey((0.0,0))((acc,value)=>(acc._1+ value,acc._2+ 1),(total1,total2)=>(total1._1 + total2._1 ,total1._2+total2._2))
    val computeav=aggrorder.map(rec=>(rec._1,BigDecimal(rec._2._1/rec._2._2).setScale(2,BigDecimal.RoundingMode.HALF_UP).toFloat))
    val sortavg=computeav.sortByKey()
    sortavg.take(10).foreach(println)
    //sortavg.saveAsTextFile(outputPath0)
  }
}
