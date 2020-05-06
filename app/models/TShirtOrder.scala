package models

import scalikejdbc._

case class TShirtOrder(
  id: Int,
  name: String,
  age: Int,
  tShirtId: Int,
  orderId: Int) {

  def save()(implicit session: DBSession = TShirtOrder.autoSession): TShirtOrder = TShirtOrder.save(this)(session)

  def destroy()(implicit session: DBSession = TShirtOrder.autoSession): Int = TShirtOrder.destroy(this)(session)

}


object TShirtOrder extends SQLSyntaxSupport[TShirtOrder] {

  override val schemaName = Some("public")

  override val tableName = "t_shirt_order"

  override val columns = Seq("id", "name", "age", "t_shirt_id", "order_id")

  def apply(tso: SyntaxProvider[TShirtOrder])(rs: WrappedResultSet): TShirtOrder = apply(tso.resultName)(rs)
  def apply(tso: ResultName[TShirtOrder])(rs: WrappedResultSet): TShirtOrder = new TShirtOrder(
    id = rs.get(tso.id),
    name = rs.get(tso.name),
    age = rs.get(tso.age),
    tShirtId = rs.get(tso.tShirtId),
    orderId = rs.get(tso.orderId)
  )

  val tso = TShirtOrder.syntax("tso")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TShirtOrder] = {
    withSQL {
      select.from(TShirtOrder as tso).where.eq(tso.id, id)
    }.map(TShirtOrder(tso.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TShirtOrder] = {
    withSQL(select.from(TShirtOrder as tso)).map(TShirtOrder(tso.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TShirtOrder as tso)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TShirtOrder] = {
    withSQL {
      select.from(TShirtOrder as tso).where.append(where)
    }.map(TShirtOrder(tso.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TShirtOrder] = {
    withSQL {
      select.from(TShirtOrder as tso).where.append(where)
    }.map(TShirtOrder(tso.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TShirtOrder as tso).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    age: Int,
    tShirtId: Int,
    orderId: Int)(implicit session: DBSession = autoSession): TShirtOrder = {
    val generatedKey = withSQL {
      insert.into(TShirtOrder).namedValues(
        column.name -> name,
        column.age -> age,
        column.tShirtId -> tShirtId,
        column.orderId -> orderId
      )
    }.updateAndReturnGeneratedKey.apply()

    TShirtOrder(
      id = generatedKey.toInt,
      name = name,
      age = age,
      tShirtId = tShirtId,
      orderId = orderId)
  }

  def batchInsert(entities: Seq[TShirtOrder])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'age -> entity.age,
        'tShirtId -> entity.tShirtId,
        'orderId -> entity.orderId))
    SQL("""insert into t_shirt_order(
      name,
      age,
      t_shirt_id,
      order_id
    ) values (
      {name},
      {age},
      {tShirtId},
      {orderId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TShirtOrder)(implicit session: DBSession = autoSession): TShirtOrder = {
    withSQL {
      update(TShirtOrder).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.age -> entity.age,
        column.tShirtId -> entity.tShirtId,
        column.orderId -> entity.orderId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TShirtOrder)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TShirtOrder).where.eq(column.id, entity.id) }.update.apply()
  }

}
