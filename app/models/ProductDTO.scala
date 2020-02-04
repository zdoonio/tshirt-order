package models

import java.time.ZonedDateTime

/**
  * Created by ${USER_dabrowski} on 04.02.2020.
  */
case class ProductDTO(id: Long, name: Option[String] = None, brand: Option[String] = None, price: Option[BigDecimal] = None,
                        category: Option[CategoryDTO] = None, createdAt: ZonedDateTime, updatedAt: ZonedDateTime, depotPrice: Option[BigDecimal] = None)
