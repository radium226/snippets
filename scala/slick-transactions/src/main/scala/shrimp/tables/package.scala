package shrimp

import slick.driver.H2Driver.api._


package object tables {

  class CarsTable(tag: Tag) extends Table[(Int, String)](tag, "CARS") {

    def id = column[Int]("ID", O.PrimaryKey)

    def name = column[String]("NAME")

    def * = (id, name)

  }

  val Cars = TableQuery[CarsTable]

  class ColorsTable(tag: Tag) extends Table[(Int, String)](tag, "COLORS") {

    def id = column[Int]("ID", O.PrimaryKey)

    def name = column[String]("NAME")

    def * = (id, name)

  }

  val Colors = TableQuery[ColorsTable]

  class CarsColorsTable(tag: Tag) extends Table[(Int, Int)](tag, "CARS_COLORS") {

    def carId = column[Int]("CAR_ID")

    def colorId = column[Int]("COLOR_ID")

    def pk = primaryKey("PK_CARS_COLORS", (carId, colorId))

    def fkCars = foreignKey("FK_CARS_COLORS_CARS", carId, Cars)(_.id)

    def fkColors = foreignKey("FK_CARS_COLORS_COLORS", colorId, Colors)(_.id)

    def * = (carId, colorId)

  }

  val CarsColors = TableQuery[CarsColorsTable]

}
