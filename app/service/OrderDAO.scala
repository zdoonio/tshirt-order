package service

import java.time.LocalDate
import models.{Order, TShirt, TShirtOrder}
import models.dto.{OrderDTO, TShirtDTO}
import scalikejdbc._

/**
  * Created by Dominik Zduńczyk on 06.05.2020.
  */
object OrderDAO {

  /**
    * Create new order with t_shirts
    */
  def create(tShirts: List[TShirtDTO]) = {
    val order = Order.create(LocalDate.now())
    tShirts.foreach { orderTshirt =>
      TShirt.findBy(sqls"color = ${orderTshirt.color} AND size = ${orderTshirt.size}").map { tshirt =>
        TShirtOrder.create(orderTshirt.name, orderTshirt.age, tshirt.id, order.id)
      }

    }
  }

  /**
    * Find all orders with Tshirts
    * @return returns all orders
    */
  def findAllOrders() : List[OrderDTO] = {
      Order.findOrderWithTshirts.map { order =>
        (OrderDTO(order.id, Some(order.createDate), List()), TShirtDTO(order.name, order.age, order.color, order.size))
    }.groupBy(_._1).mapValues(_.map(_._2)).map { data =>
        OrderDTO(data._1.id, data._1.createDate, data._2)
      }.toList
  }
}
