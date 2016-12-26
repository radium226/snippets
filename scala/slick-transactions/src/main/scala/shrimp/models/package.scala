package shrimp

/**
  * Created by adrien on 12/25/16.
  */
package object models {

  case class Car(id: Option[Int], name: String, colors: Seq[Color])

  case class Color(id: Option[Int], name: String)

}
