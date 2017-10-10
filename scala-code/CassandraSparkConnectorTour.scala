import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import com.datastax.spark.connector.UDTValue 
import org.apache.spark.sql.types._
import com.datastax.driver.core.Session
object CassandraSparkConnectorTour  {
  def main(args: Array[String]): Unit = {
    //http://stackoverflow.com/questions/39248090/spark-sql-to-insert-data-into-cassandra
    val conf = new SparkConf().setAppName("CassandraSparkConnectorTour").setMaster("local")
    .set("spark.cassandra.connection.host", "127.0.0.1")
      val sc = new SparkContext(conf)
    val collection = sc.parallelize(Seq(model("srinadh", "chicken"),model("Vamsirishna", "prawns")))

 //then save to cassandra
 //collection.saveToCassandra("keyspace_name", "table_name", SomeColumns("col name", "col name"))

//collection.saveToCassandra("my_keyspace", "user", SomeColumns("name", "favorite_food"))
val sqlContext= new org.apache.spark.sql.SQLContext(sc)
/*val results = sqlContext.sql("SELECT * from my_keyspace.user")
results.collect().foreach(println)*/
    //http://stackoverflow.com/questions/32451614/reading-from-cassandra-using-spark-streaming
       val rdd = sc.cassandraTable("my_keyspace", "user").select("name", "favorite_food").map(row => (row.getString("name"),row.getString("favorite_food"))).collect().foreach(println)
       //rdd.collect().foreach(println)


  }
  //we need define a class
//case class name(column name: datatype,column name: datatype)
//CREATE TABLE my_keyspace.user (name TEXT PRIMARY KEY,favorite_food TEXT )
case class model(name: String, favorite_food: String)
//http://stackoverflow.com/questions/31415250/datastax-java-driver-convert-scala-collections-to-java-error
//https://docs.datastax.com/en/datastax_enterprise/4.7/datastax_enterprise/spark/sparkSave.html
}