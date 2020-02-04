package controllers

import javax.inject._
import mapper.JsonWriter
import models.{Category, CategoryDTO, Product, ProductDTO}
import play.api.libs.json.Json
import play.api.mvc._
import scalikejdbc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class DemoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def getProducts = Action { implicit request =>
    val products = Product.findAll()

    Ok(Json.obj("products" -> products.map ( product =>
      JsonWriter.productWrites.writes(ProductDTO(product.id, product.name, product.brand, product.price,
        Some(CategoryDTO(Category.findBy(sqls"id = ${product.categoryId}"))),
        product.createdAt, product.updatedAt, product.depotPrice))
    )))
  }

}
