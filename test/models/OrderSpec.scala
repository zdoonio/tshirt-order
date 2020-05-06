package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._
import java.time.{LocalDate}


class OrderSpec extends Specification {

  "Order" should {

    val o = Order.syntax("o")

    "find by primary keys" in new AutoRollback {
      val maybeFound = Order.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = Order.findBy(sqls.eq(o.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = Order.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = Order.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = Order.findAllBy(sqls.eq(o.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = Order.countBy(sqls.eq(o.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = Order.create(createDate = LocalDate.now)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = Order.findAll().head
      // TODO modify something
      val modified = entity
      val updated = Order.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = Order.findAll().head
      val deleted = Order.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = Order.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = Order.findAll()
      entities.foreach(e => Order.destroy(e))
      val batchInserted = Order.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
