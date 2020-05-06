package models

import scalikejdbc._

case class Stock(
  id: Int,
  itemNumber: Int,
  tShirtId: Int) {

  def save()(implicit session: DBSession = Stock.autoSession): Stock = Stock.save(this)(session)

  def destroy()(implicit session: DBSession = Stock.autoSession): Int = Stock.destroy(this)(session)

}


object Stock extends SQLSyntaxSupport[Stock] {

  override val schemaName = Some("public")

  override val tableName = "stock"

  override val columns = Seq("id", "item_number", "t_shirt_id")

  def apply(s: SyntaxProvider[Stock])(rs: WrappedResultSet): Stock = apply(s.resultName)(rs)
  def apply(s: ResultName[Stock])(rs: WrappedResultSet): Stock = new Stock(
    id = rs.get(s.id),
    itemNumber = rs.get(s.itemNumber),
    tShirtId = rs.get(s.tShirtId)
  )

  val s = Stock.syntax("s")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Stock] = {
    withSQL {
      select.from(Stock as s).where.eq(s.id, id)
    }.map(Stock(s.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Stock] = {
    withSQL(select.from(Stock as s)).map(Stock(s.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Stock as s)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Stock] = {
    withSQL {
      select.from(Stock as s).where.append(where)
    }.map(Stock(s.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Stock] = {
    withSQL {
      select.from(Stock as s).where.append(where)
    }.map(Stock(s.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Stock as s).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    itemNumber: Int,
    tShirtId: Int)(implicit session: DBSession = autoSession): Stock = {
    val generatedKey = withSQL {
      insert.into(Stock).namedValues(
        column.itemNumber -> itemNumber,
        column.tShirtId -> tShirtId
      )
    }.updateAndReturnGeneratedKey.apply()

    Stock(
      id = generatedKey.toInt,
      itemNumber = itemNumber,
      tShirtId = tShirtId)
  }

  def batchInsert(entities: Seq[Stock])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'itemNumber -> entity.itemNumber,
        'tShirtId -> entity.tShirtId))
    SQL("""insert into stock(
      item_number,
      t_shirt_id
    ) values (
      {itemNumber},
      {tShirtId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Stock)(implicit session: DBSession = autoSession): Stock = {
    withSQL {
      update(Stock).set(
        column.id -> entity.id,
        column.itemNumber -> entity.itemNumber,
        column.tShirtId -> entity.tShirtId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Stock)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Stock).where.eq(column.id, entity.id) }.update.apply()
  }

}
