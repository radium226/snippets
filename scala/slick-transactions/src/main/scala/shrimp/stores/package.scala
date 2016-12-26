package shrimp

import tables._
import models._
import slick.dbio.DBIOAction
import slick.dbio.Effect.{Read, Write}
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext.Implicits.global

package object stores {

  object CarStore {

    def storeCar(car: Car) = {
      (car.id match {
        case Some(carId) => DBIO.successful(carId)
        case None => {
          tables.Cars.map(_.id).max.result
            .map({
              case Some(maxCarId) => maxCarId + 1
              case None => 0
            })
            .flatMap({ carId => {
              println(s"carId=${carId}")
              (tables.Cars += (carId, car.name))
                .flatMap({ _ =>
                  DBIO.successful(carId)
                })
            }})
        }
      })
        .flatMap({ carId =>
          DBIO.sequence(
            car.colors.map({ color =>
              (color match {
                case Color(Some(colorId), _) => DBIO.successful(colorId)
                case Color(None, _) => {
                  tables.Colors.map(_.id).max.result
                    .map({
                      case Some(maxColorId) => maxColorId + 1
                      case None => 0
                    })
                    .flatMap({ colorId =>
                      println(s"colorId=${colorId}")
                      (tables.Colors += (colorId, color.name))
                        .flatMap({ _ =>
                          DBIO.successful(colorId)
                        })
                    })
                }
              })
                .flatMap({ colorId =>
                  println(s"carId=${carId} / colorId=${colorId}")
                  (tables.CarsColors += (carId, colorId))
                    .map({ _ => Color(Some(colorId), color.name) })
                })
            })
          ).map({ colors =>
            Car(Some(carId), car.name, colors)
          })
        })
    }
  }

}
