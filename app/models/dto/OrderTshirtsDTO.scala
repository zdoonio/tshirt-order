package models.dto

import java.time.LocalDate
/**
  * Created by Dominik Zduńczyk on 06.05.2020.
  */
case class OrderTshirtsDTO(
           id: Int,
           createDate: LocalDate,
           name: String,
           age: Int,
           color: String,
           size: String)
