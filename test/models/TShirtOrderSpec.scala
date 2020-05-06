package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class TShirtOrderSpec extends Specification {

  "TShirtOrder" should {

    val tso = TShirtOrder.syntax("tso")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TShirtOrder.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TShirtOrder.findBy(sqls.eq(tso.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TShirtOrder.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TShirtOrder.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TShirtOrder.findAllBy(sqls.eq(tso.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TShirtOrder.countBy(sqls.eq(tso.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = TShirtOrder.create(name = "MyString", age = 123, tShirtId = 123, orderId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = TShirtOrder.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TShirtOrder.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TShirtOrder.findAll().head
      val deleted = TShirtOrder.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = TShirtOrder.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TShirtOrder.findAll()
      entities.foreach(e => TShirtOrder.destroy(e))
      val batchInserted = TShirtOrder.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
