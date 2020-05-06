package models.dto

import java.time.LocalDate

/**
  * Created by Dominik Zduńczyk on 06.05.20.
  */
case class OrderDTO(
   id: Int,
   createDate: Option[LocalDate],
   tShirts: List[TShirtDTO]
)

