package mapper

import models.dto.TShirtDTO
import play.api.libs.json.Json

/**
  * Created by Dominik Zduńczyk on 06.05.2020.
  */
object JsonReader {

  implicit val tShirtDataReads = Json.reads[TShirtDTO]

}
