package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class StockSpec extends Specification {

  "Stock" should {

    val s = Stock.syntax("s")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Stock.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Stock.findBy(sqls.eq(s.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Stock.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Stock.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Stock.findAllBy(sqls.eq(s.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Stock.countBy(sqls.eq(s.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Stock.create(itemNumber = 123, tShirtId = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Stock.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Stock.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Stock.findAll().head
      val deleted = Stock.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Stock.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Stock.findAll()
      entities.foreach(e => Stock.destroy(e))
      val batchInserted = Stock.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
