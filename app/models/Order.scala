package models

import scalikejdbc._
import java.time.{LocalDate}

case class Order(
  id: Int,
  tShirtId: Int,
  name: String,
  age: Int,
  createDate: LocalDate) {

  def save()(implicit session: DBSession = Order.autoSession): Order = Order.save(this)(session)

  def destroy()(implicit session: DBSession = Order.autoSession): Int = Order.destroy(this)(session)

}


object Order extends SQLSyntaxSupport[Order] {

  override val schemaName = Some("public")

  override val tableName = "orders"

  override val columns = Seq("id", "t_shirt_id", "name", "age", "create_date")

  def apply(o: SyntaxProvider[Order])(rs: WrappedResultSet): Order = apply(o.resultName)(rs)
  def apply(o: ResultName[Order])(rs: WrappedResultSet): Order = new Order(
    id = rs.get(o.id),
    tShirtId = rs.get(o.tShirtId),
    name = rs.get(o.name),
    age = rs.get(o.age),
    createDate = rs.get(o.createDate)
  )

  val o = Order.syntax("o")

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
    tShirtId: Int,
    name: String,
    age: Int,
    createDate: LocalDate)(implicit session: DBSession = autoSession): Order = {
    val generatedKey = withSQL {
      insert.into(Order).namedValues(
        column.tShirtId -> tShirtId,
        column.name -> name,
        column.age -> age,
        column.createDate -> createDate
      )
    }.updateAndReturnGeneratedKey.apply()

    Order(
      id = generatedKey.toInt,
      tShirtId = tShirtId,
      name = name,
      age = age,
      createDate = createDate)
  }

  def batchInsert(entities: Seq[Order])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'tShirtId -> entity.tShirtId,
        'name -> entity.name,
        'age -> entity.age,
        'createDate -> entity.createDate))
    SQL("""insert into orders(
      t_shirt_id,
      name,
      age,
      create_date
    ) values (
      {tShirtId},
      {name},
      {age},
      {createDate}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Order)(implicit session: DBSession = autoSession): Order = {
    withSQL {
      update(Order).set(
        column.id -> entity.id,
        column.tShirtId -> entity.tShirtId,
        column.name -> entity.name,
        column.age -> entity.age,
        column.createDate -> entity.createDate
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Order)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Order).where.eq(column.id, entity.id) }.update.apply()
  }

}
