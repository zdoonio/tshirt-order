package models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class Category(
  id: Long,
  name: Option[String] = None,
  createdAt: ZonedDateTime,
  updatedAt: ZonedDateTime) {

  def save()(implicit session: DBSession = Category.autoSession): Category = Category.save(this)(session)

  def destroy()(implicit session: DBSession = Category.autoSession): Int = Category.destroy(this)(session)

}


object Category extends SQLSyntaxSupport[Category] {

  override val schemaName = Some("public")

  override val tableName = "categories"

  override val columns = Seq("id", "name", "created_at", "updated_at")

  def apply(c: SyntaxProvider[Category])(rs: WrappedResultSet): Category = apply(c.resultName)(rs)
  def apply(c: ResultName[Category])(rs: WrappedResultSet): Category = new Category(
    id = rs.get(c.id),
    name = rs.get(c.name),
    createdAt = rs.get(c.createdAt),
    updatedAt = rs.get(c.updatedAt)
  )

  val c = Category.syntax("c")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[Category] = {
    withSQL {
      select.from(Category as c).where.eq(c.id, id)
    }.map(Category(c.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Category] = {
    withSQL(select.from(Category as c)).map(Category(c.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Category as c)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Category] = {
    withSQL {
      select.from(Category as c).where.append(where)
    }.map(Category(c.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Category] = {
    withSQL {
      select.from(Category as c).where.append(where)
    }.map(Category(c.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Category as c).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None,
    createdAt: ZonedDateTime,
    updatedAt: ZonedDateTime)(implicit session: DBSession = autoSession): Category = {
    val generatedKey = withSQL {
      insert.into(Category).namedValues(
        column.name -> name,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt
      )
    }.updateAndReturnGeneratedKey.apply()

    Category(
      id = generatedKey,
      name = name,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }

  def batchInsert(entities: Seq[Category])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt))
    SQL("""insert into categories(
      name,
      created_at,
      updated_at
    ) values (
      {name},
      {createdAt},
      {updatedAt}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Category)(implicit session: DBSession = autoSession): Category = {
    withSQL {
      update(Category).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Category)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Category).where.eq(column.id, entity.id) }.update.apply()
  }

}
