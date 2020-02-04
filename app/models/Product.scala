package models

import scalikejdbc._
import java.time.{ZonedDateTime}

case class Product(
  id: Long,
  name: Option[String] = None,
  brand: Option[String] = None,
  price: Option[BigDecimal] = None,
  categoryId: Option[Long] = None,
  createdAt: ZonedDateTime,
  updatedAt: ZonedDateTime,
  depotPrice: Option[BigDecimal] = None) {

  def save()(implicit session: DBSession = Product.autoSession): Product = Product.save(this)(session)

  def destroy()(implicit session: DBSession = Product.autoSession): Int = Product.destroy(this)(session)

}


object Product extends SQLSyntaxSupport[Product] {

  override val schemaName = Some("public")

  override val tableName = "products"

  override val columns = Seq("id", "name", "brand", "price", "category_id", "created_at", "updated_at", "depot_price")

  def apply(p: SyntaxProvider[Product])(rs: WrappedResultSet): Product = apply(p.resultName)(rs)
  def apply(p: ResultName[Product])(rs: WrappedResultSet): Product = new Product(
    id = rs.get(p.id),
    name = rs.get(p.name),
    brand = rs.get(p.brand),
    price = rs.get(p.price),
    categoryId = rs.get(p.categoryId),
    createdAt = rs.get(p.createdAt),
    updatedAt = rs.get(p.updatedAt),
    depotPrice = rs.get(p.depotPrice)
  )

  val p = Product.syntax("p")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[Product] = {
    withSQL {
      select.from(Product as p).where.eq(p.id, id)
    }.map(Product(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Product] = {
    withSQL(select.from(Product as p)).map(Product(p.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Product as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Product] = {
    withSQL {
      select.from(Product as p).where.append(where)
    }.map(Product(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Product] = {
    withSQL {
      select.from(Product as p).where.append(where)
    }.map(Product(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Product as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: Option[String] = None,
    brand: Option[String] = None,
    price: Option[BigDecimal] = None,
    categoryId: Option[Long] = None,
    createdAt: ZonedDateTime,
    updatedAt: ZonedDateTime,
    depotPrice: Option[BigDecimal] = None)(implicit session: DBSession = autoSession): Product = {
    val generatedKey = withSQL {
      insert.into(Product).namedValues(
        column.name -> name,
        column.brand -> brand,
        column.price -> price,
        column.categoryId -> categoryId,
        column.createdAt -> createdAt,
        column.updatedAt -> updatedAt,
        column.depotPrice -> depotPrice
      )
    }.updateAndReturnGeneratedKey.apply()

    Product(
      id = generatedKey,
      name = name,
      brand = brand,
      price = price,
      categoryId = categoryId,
      createdAt = createdAt,
      updatedAt = updatedAt,
      depotPrice = depotPrice)
  }

  def batchInsert(entities: Seq[Product])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'name -> entity.name,
        'brand -> entity.brand,
        'price -> entity.price,
        'categoryId -> entity.categoryId,
        'createdAt -> entity.createdAt,
        'updatedAt -> entity.updatedAt,
        'depotPrice -> entity.depotPrice))
    SQL("""insert into products(
      name,
      brand,
      price,
      category_id,
      created_at,
      updated_at,
      depot_price
    ) values (
      {name},
      {brand},
      {price},
      {categoryId},
      {createdAt},
      {updatedAt},
      {depotPrice}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Product)(implicit session: DBSession = autoSession): Product = {
    withSQL {
      update(Product).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.brand -> entity.brand,
        column.price -> entity.price,
        column.categoryId -> entity.categoryId,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.depotPrice -> entity.depotPrice
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Product)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(Product).where.eq(column.id, entity.id) }.update.apply()
  }

}
