package models

import scalikejdbc._

case class TShirt(
  id: Int,
  color: String,
  size: String) {

  def save()(implicit session: DBSession = TShirt.autoSession): TShirt = TShirt.save(this)(session)

  def destroy()(implicit session: DBSession = TShirt.autoSession): Int = TShirt.destroy(this)(session)

}


object TShirt extends SQLSyntaxSupport[TShirt] {

  override val schemaName = Some("public")

  override val tableName = "t_shirts"

  override val columns = Seq("id", "color", "size")

  def apply(ts: SyntaxProvider[TShirt])(rs: WrappedResultSet): TShirt = apply(ts.resultName)(rs)
  def apply(ts: ResultName[TShirt])(rs: WrappedResultSet): TShirt = new TShirt(
    id = rs.get(ts.id),
    color = rs.get(ts.color),
    size = rs.get(ts.size)
  )

  val ts = TShirt.syntax("ts")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[TShirt] = {
    withSQL {
      select.from(TShirt as ts).where.eq(ts.id, id)
    }.map(TShirt(ts.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[TShirt] = {
    withSQL(select.from(TShirt as ts)).map(TShirt(ts.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(TShirt as ts)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[TShirt] = {
    withSQL {
      select.from(TShirt as ts).where.append(where)
    }.map(TShirt(ts.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[TShirt] = {
    withSQL {
      select.from(TShirt as ts).where.append(where)
    }.map(TShirt(ts.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(TShirt as ts).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    color: String,
    size: String)(implicit session: DBSession = autoSession): TShirt = {
    val generatedKey = withSQL {
      insert.into(TShirt).namedValues(
        column.color -> color,
        column.size -> size
      )
    }.updateAndReturnGeneratedKey.apply()

    TShirt(
      id = generatedKey.toInt,
      color = color,
      size = size)
  }

  def batchInsert(entities: Seq[TShirt])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'color -> entity.color,
        'size -> entity.size))
    SQL("""insert into t_shirts(
      color,
      size
    ) values (
      {color},
      {size}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: TShirt)(implicit session: DBSession = autoSession): TShirt = {
    withSQL {
      update(TShirt).set(
        column.id -> entity.id,
        column.color -> entity.color,
        column.size -> entity.size
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: TShirt)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(TShirt).where.eq(column.id, entity.id) }.update.apply()
  }

}
