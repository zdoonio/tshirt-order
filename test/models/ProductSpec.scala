package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{ZonedDateTime}


class ProductSpec extends Specification {

  "Product" should {

    val p = Product.syntax("p")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Product.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Product.findBy(sqls.eq(p.id, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Product.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Product.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Product.findAllBy(sqls.eq(p.id, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Product.countBy(sqls.eq(p.id, 1L))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Product.create(createdAt = null, updatedAt = null)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Product.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Product.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Product.findAll().head
      val deleted = Product.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Product.find(1L)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Product.findAll()
      entities.foreach(e => Product.destroy(e))
      val batchInserted = Product.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
