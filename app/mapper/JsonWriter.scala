package mapper

import models.dto.{OrderDTO, ResponseDTO, TShirtDTO}
import play.api.libs.json.{Json, Writes}


/**
  * Created by Dominik Zduńczyk on 04.02.2020.
  */
object JsonWriter {

  implicit val ordersWrites: Writes[OrderDTO] = (data: OrderDTO) => Json.obj(
    "id" -> data.id,
    "date" -> data.createDate,
    "tshirts" -> data.tShirts
  )

  implicit val tshirtsWrites: Writes[TShirtDTO] = (data: TShirtDTO) => Json.obj(
    "name" -> data.name,
    "age" -> data.age,
    "color" -> data.color,
    "size" -> data.size
  )

  implicit val responseWrites: Writes[ResponseDTO[List[OrderDTO]]] = (data: ResponseDTO[List[OrderDTO]]) => Json.obj(
    "code" -> data.code,
    "result" -> data.result,
    "message" -> data.message
  )

  implicit val responseBadRequestWrites: Writes[ResponseDTO[String]] = (data: ResponseDTO[String]) => Json.obj(
    "code" -> data.code,
    "result" -> data.result,
    "message" -> data.message
  )

}