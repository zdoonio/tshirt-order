package models

import java.time.ZonedDateTime

/**
  * Created by ${USER_dabrowski} on 04.02.2020.
  */
case class CategoryDTO(category: Option[Category]) {
  val id: Option[Long] = category.map(_.id)
  val name: Option[String] = category.map(_.name.orNull)
}
