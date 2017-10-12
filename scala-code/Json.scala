package com.emc.demo
import scala.collection.mutable._
import net.liftweb.json._
import net.liftweb.json.Serialization.write
import net.liftweb.json.JsonAST
import net.liftweb.json.JsonDSL._
import net.liftweb.json.Printer.{compact,pretty}
object JsonDemo {
  def main(args: Array[String]): Unit = {
    val json = List(1, 2, 3)
    println(compact(JsonAST.render(json)))

    val map = Map("fname" -> "Alvin", "lname" -> "Alexander")
    println(compact(JsonAST.render(map)))
  }
}