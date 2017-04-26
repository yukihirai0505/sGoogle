package com.yukihirai0505.sGoogle

import com.yukihirai0505.sGoogle.http.{Request, Verbs}
import com.yukihirai0505.sGoogle.model.{Constants, Methods, OAuthConstants}
import com.yukihirai0505.sGoogle.responses.calendarList.CalendarList
import dispatch._
import play.api.libs.json.Reads

import scala.language.postfixOps


/**
  * author Yuki Hirai on 2016/11/09.
  */
class Google(accessToken: String) {

  protected def concatMapOpt(postData: Option[Map[String,String]], params: Map[String,Option[String]])
  : Map[String,Option[String]] = postData match {
    case Some(m) => params ++ m.mapValues(Some(_))
    case _ => params
  }

  def request[T](verb: Verbs, apiPath: String, params: Option[Map[String, Option[String]]] = None)(implicit r: Reads[T]): Future[Option[T]] = {
    val parameters: Map[String, String] = params match {
      case Some(m) => m.filter(_._2.isDefined).mapValues(_.getOrElse("")).filter(!_._2.isEmpty)
      case None => Map.empty
    }
    val effectiveUrl = s"${Constants.API_URL}$apiPath?"
    val request: Req = url(effectiveUrl)
      .setMethod(verb.label)
      .setHeader("Authorization", s"Bearer $accessToken")
    val requestWithParams = if (verb.label == Verbs.GET.label) { request <<? parameters } else { request << parameters }
    println(requestWithParams.url)
    Request.send[T](requestWithParams)
  }

  def getCalendarList(calendarId: String = "primary"): Future[Option[CalendarList]] = {
    val apiPath: String = Methods.CALENDAR_LIST format calendarId
    request[CalendarList](Verbs.GET, apiPath)
  }
}