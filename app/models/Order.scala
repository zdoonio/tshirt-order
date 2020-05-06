package models

import scalikejdbc._
import java.time.LocalDate

import models.dto.{OrderDTO, OrderTshirtsDTO}

case class Order(
  id: Int,
  createDate: LocalDate) {

  def save()(implicit session: DBSession = Order.autoSession): Order = Order.save(this)(session)

  def destroy()(implicit session: DBSession = Order.autoSession): Int = Order.destroy(this)(session)

}


object Order extends SQLSyntaxSupport[Order] {

  override val schemaName = Some("public")

  override val tableName = "orders"

  override val columns = Seq("id", "create_date")

  def apply(o: SyntaxProvider[Order])(rs: WrappedResultSet): Order = apply(o.resultName)(rs)
  def apply(o: ResultName[Order])(rs: WrappedResultSet): Order = new Order(
    id = rs.get(o.id),
    createDate = rs.get(o.createDate)
  )

  val o = Order.syntax("o")
  val tso = TShirtOrder.syntax("tso")
  val to = TShirt.syntax("to")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Order] = {
    withSQL {
      select.from(Order as o).where.eq(o.id, id)
    }.map(Order(o.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Order] = {
    withSQL(select.from(Order as o)).map(Order(o.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Order as o)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Order] = {
    withSQL {
      select.from(Order as o).where.append(where)
    }.map(Order(o.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Order] = {
    withSQL {
      select.from(Order as o).where.append(where)
    }.map(Order(o.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Order as o).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    createDate: LocalDate)(implicit session: DBSession = autoSession): Order = {
    val generatedKey = withSQL {
      insert.into(Order).namedValues(
        column.createDate -> createDate
      )
    }.updateAndReturnGeneratedKey.apply()

    Order(
      id = generatedKey.toInt,
      createDate = createDate)
  }

  def batchInsert(entities: Seq[Order])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'createDate -> entity.createDate))
    SQL("""insert into orders(
      create_date
    ) values (
      {createDate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Order)(implicit session: DBSession = autoSession): Order = {
    withSQL {
      update(Order).set(
        column.id -> entity.id,
        column.createDate -> entity.createDate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Order)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Order).where.eq(column.id, entity.id) }.update.apply()
  }

  //    SQL("""
  //          SELECT o.id, o.create_date, tso.name, tso.age, ts.color, ts.size
  //          FROM Orders as o LEFT JOIN T_shirt_order as tso ON tso.order_id = o.id
  //          LEFT JOIN t_shirts as ts ON ts.id = tso.t_shirt_id
  //      """
  //    )

  //    val orders = Order.findAll()
  //
  //    orders.flatMap{ order =>
  //      TShirtOrder.findAllBy(sqls"order_id = ${order.id}").map { tshirtOrder =>
  //        TShirt.findBy(sqls"id=${tshirtOrder.tShirtId}")
  //
  //        OrderDTO(order.id, order.createDate, )
  //      }
  //  }

  /**
    *
    * @param session
    * @return
    */
  def findOrderWithTshirts()(implicit session: DBSession = autoSession): List[OrderTshirtsDTO] = {
    withSQL {
      select(o.result.id, o.result.createDate, tso.result.name, tso.result.age, to.result.color, to.result.size)
        .from(Order as o)
        .leftJoin(TShirtOrder as tso).on(o.id, tso.orderId)
        .leftJoin(TShirt as to).on(tso.tShirtId, to.id)
    }.map { obj =>
      OrderTshirtsDTO(obj.int(1), obj.localDate(2),
        obj.string(3), obj.int(4),
        obj.string(5), obj.string(6))
    }.list.apply()

  }

}
