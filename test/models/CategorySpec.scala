package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{ZonedDateTime}


class CategorySpec extends Specification {

  "Category" should {

    val c = Category.syntax("c")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Category.find(1L)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Category.findBy(sqls.eq(c.id, 1L))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Category.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Category.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Category.findAllBy(sqls.eq(c.id, 1L))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Category.countBy(sqls.eq(c.id, 1L))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Category.create(createdAt = null, updatedAt = null)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Category.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Category.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Category.findAll().head
      val deleted = Category.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Category.find(1L)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Category.findAll()
      entities.foreach(e => Category.destroy(e))
      val batchInserted = Category.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
