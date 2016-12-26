package shrimp

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import slick.driver.H2Driver.api._
import stores._
import models._

object TrySlick {

  val LongDuration = 1 minute

  lazy val Logger = LoggerFactory.getLogger("TrySlick")

  val ConfigContent = """
                        |h2 = {
                        |  url = "jdbc:h2:mem:test1"
                        |  driver = org.h2.Driver
                        |  connectionPool = disabled
                        |  keepAliveConnection = true
                        |}
                      """.stripMargin

  def main(arguments: Array[String]): Unit = {
    val db = Database.forConfig("h2", ConfigFactory.parseString(ConfigContent))
    createTables(db)
    Logger.info("Table created")

    Await.result(db.run((tables.Colors ++= Seq(
      (7, "Bleu"),
      (8, "Blanc"),
      (1, "Rouge")
    )).transactionally), LongDuration)

    val colors = Seq(
        Color(Some(7), "Bleu"),
        Color(Some(8), "Blanc"),
        Color(Some(11), "Rouge")
      )

    val car = Car(None, "Toyota", colors)

    val r1 = db.run(CarStore.storeCar(car).transactionally)
    r1.onFailure({
      case _: Throwable => println("Holly fuck! ")
    })
    val savedCar = Await.result(r1, LongDuration)
  }

  def createTables(db: Database) = {
    Await.result(db.run((tables.Cars.schema ++ tables.Colors.schema ++ tables.CarsColors.schema).create), LongDuration)
  }

  def sleep(duration: Duration): Unit = {
    Thread.sleep(duration.toMillis)
  }


}
