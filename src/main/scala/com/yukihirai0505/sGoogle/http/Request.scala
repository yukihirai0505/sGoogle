package com.yukihirai0505.sGoogle.http

import dispatch.Defaults._
import dispatch._
import play.api.libs.json._

object Request {

  /**
    * Send the prepared request to an URL and parse the response to an appropriate case class.
    *
    * @param request Req, the dispatch request ready to by sent
    * @tparam T represent the type of the facebook data requested
    * @return a Future of Response[T]
    */
  def send[T](request: Req)(implicit r: Reads[T]): Future[Option[T]] = {
    Http(request).map { resp =>
      val response = resp.getResponseBody
      if (resp.getStatusCode != 200 && resp.getStatusCode != 204) throw new Exception(response.toString)
      if (response.isEmpty) None
      else {
        Json.parse(response).validate[T] match {
          case JsError(e) => throw new Exception(e.toString())
          case JsSuccess(value, _) => value match {
            case None => None
            case _ => Some(value)
          }
        }
      }
    }
  }

}