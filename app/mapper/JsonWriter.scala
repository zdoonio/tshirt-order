package mapper

import models.{CategoryDTO, ProductDTO}
import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by Dominik Zduńczyk on 04.02.2020.
  */
object JsonWriter {

    implicit val categoryWrites: Writes[CategoryDTO] = (category: CategoryDTO) => Json.obj(
      "id" -> category.id,
      "name" -> category.name
    )

    implicit val productWrites: Writes[ProductDTO] = (product: ProductDTO) => Json.obj(
      "id" -> product.id,
      "name" -> product.name,
      "brand" -> product.brand,
      "price" -> product.price,
      "category" -> product.category.map(category =>
        Json.obj(
          "id" -> category.id,
          "name" -> category.name
        )
      ),
      "created_at" -> product.createdAt,
      "updated_at" -> product.updatedAt,
      "depot_price" -> product.depotPrice
    )

  }