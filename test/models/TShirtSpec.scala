package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class TShirtSpec extends Specification {

  "TShirt" should {

    val ts = TShirt.syntax("ts")

    "find by primary keys" in new AutoRollback {
      val maybeFound = TShirt.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = TShirt.findBy(sqls.eq(ts.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = TShirt.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = TShirt.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = TShirt.findAllBy(sqls.eq(ts.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = TShirt.countBy(sqls.eq(ts.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = TShirt.create(color = "MyString", size = "MyString")
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = TShirt.findAll().head
      // TODO modify something
      val modified = entity
      val updated = TShirt.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = TShirt.findAll().head
      val deleted = TShirt.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = TShirt.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = TShirt.findAll()
      entities.foreach(e => TShirt.destroy(e))
      val batchInserted = TShirt.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
