package service

import java.time.LocalDate

import models.{Order, Stock, TShirt, TShirtOrder}
import models.dto.{OrderDTO, TShirtDTO}
import scalikejdbc._

/**
  * Created by Dominik Zduńczyk on 06.05.2020.
  */
object OrderDAO {

  /**
    * Create new order with t_shirts
    */
  def create(tShirts: List[TShirtDTO]) {
    val order = Order.create(LocalDate.now())
    tShirts.flatMap { orderTshirt =>
      TShirt.findBy(sqls"color = ${orderTshirt.color} AND size = ${orderTshirt.size}").map { tshirt =>
        Stock.findBy(sqls"t_shirt_id = ${tshirt.id}").map { stock =>
          if (stock.itemNumber > 0) {
            TShirtOrder.create(orderTshirt.name, orderTshirt.age, tshirt.id, order.id)
            Stock.save(Stock(stock.id, stock.itemNumber - 1, stock.tShirtId))
            "Utworzono"
          } else
            s"Brak koszulek koloru ${orderTshirt.color} w magazynie"
        }.orNull
      }

    }
  }

  /**
    * Checks that tshirt is avaliable
    */
  def isTshirtAvaliable(tshirt: TShirtDTO): Boolean = {
    TShirt.findBy(sqls"color = ${tshirt.color} AND size = ${tshirt.size}").flatMap { tshirt =>
      Stock.findBy(sqls"t_shirt_id = ${tshirt.id}").map { stock =>
        if (stock.itemNumber > 0)
          true
        else
          false
      }
    }.getOrElse(false)
  }
  /**
    * Find all orders with Tshirts
    *
    * @return returns all orders
    */
  def findAllOrders(): List[OrderDTO] = {
    Order.findOrderWithTshirts.map { order =>
      (OrderDTO(order.id, Some(order.createDate), List()), TShirtDTO(order.name, order.age, order.color, order.size))
    }.groupBy(_._1).mapValues(_.map(_._2)).map { data =>
      OrderDTO(data._1.id, data._1.createDate, data._2)
    }.toList
  }
}
