package controllers

import javax.inject._
import models.dto.{ResponseDTO, TShirtDTO}
import play.api.mvc._
import service.OrderDAO
import mapper.JsonReader._
import mapper.JsonWriter

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
    * Gets order list from DB
    *
    * @return Json with Order List
    */
  def getOrders = Action { implicit request =>
    val orders = OrderDAO.findAllOrders()

    Ok(JsonWriter.responseWrites.writes(ResponseDTO(Some(200), Some(orders), Some(s"Response list"))))
  }

  /**
    * Creates new order
    */
  def createOrder = Action(parse.json) { implicit request =>
    request.body.validate[List[TShirtDTO]].fold(
      error => BadRequest(JsonWriter.responseBadRequestWrites.writes(ResponseDTO(Some(400), Some("Bad request"), Some(s"Json was not correct, message: ${error.toString()}")))),
      orderData => {
        OrderDAO.create(orderData)
        Created.withHeaders(LOCATION -> "Created")
      }
    )
  }

  /**
    * Chceks avalibility tshirt
    */
  def checkAvaliability = Action(parse.json) { implicit request =>
    request.body.validate[TShirtDTO].fold(
      error => InternalServerError,

      tShirt => {
        OrderDAO.isTshirtAvaliable(tShirt) match {
          case true => Ok(JsonWriter.responseBadRequestWrites.writes(ResponseDTO(Some(200), Some("Ok"), Some("Można utworzyć"))))

          case false => Ok(JsonWriter.responseBadRequestWrites.writes(ResponseDTO(Some(400), Some("Bad request"), Some(s"Brak koszulek koloru: ${tShirt.color} oraz rozmiaru: ${tShirt.size}"))))
        }
      }
    )
  }

}
