package com.yukihirai0505.sGoogle.http

import com.ning.http.client.FluentCaseInsensitiveStringsMap
import dispatch.Defaults._
import dispatch._
import play.api.libs.json._

import scala.collection.JavaConverters._

object Request {

  /**
   * Send the prepared request to an URL and parse the response to an appropriate case class.
   *
   * @param request Req, the dispatch request ready to by sent
   * @tparam T      represent the type of the facebook data requested
   * @return        a Future of Response[T]
   */
  def send[T](request: Req)(implicit r: Reads[T]): Future[Response[T]] = {
    Http(request).map { resp =>
      val response = resp.getResponseBody
      val headers = ningHeadersToMap(resp.getHeaders)
      if (resp.getStatusCode != 200) throw new Exception(response.toString)
      // println(Json.prettyPrint(Json.parse(response)))
      val body = Json.parse(response).validate[T] match {
        case JsError(e) => throw new Exception(e.toString())
        case JsSuccess(value, _) => value match {
          case None => None
          case _ => Some(value)
        }
      }
      Response[T](body, resp.getStatusCode, headers)
    }
  }

  private def ningHeadersToMap(headers: FluentCaseInsensitiveStringsMap) = {
    mapAsScalaMapConverter(headers).asScala.map(e => e._1 -> e._2.asScala).toMap
  }

}