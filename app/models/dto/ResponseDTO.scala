package models.dto

/**
  * Created by Dominik Zduńczyk on 04.03.2020.
  *
  * Response DTO case class
  */
case class ResponseDTO[A](code : Option[Int], result : Option[A], message : Option[String])
