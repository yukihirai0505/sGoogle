package com.yukihirai0505.sGoogle

import com.yukihirai0505.sGoogle.http.{Request, Verbs}
import com.yukihirai0505.sGoogle.model.{Constants, Methods}
import com.yukihirai0505.sGoogle.responses.calendarList.{CalendarList, CalendarListList, DefaultReminders, NotificationSettings}
import dispatch._
import play.api.libs.json.{Json, Reads}

import scala.language.postfixOps


/**
  * author Yuki Hirai on 2016/11/09.
  */
class Google(accessToken: String) {

  protected def concatMapOpt(postData: Option[Map[String, String]], params: Map[String, Option[String]])
  : Map[String, Option[String]] = postData match {
    case Some(m) => params ++ m.mapValues(Some(_))
    case _ => params
  }

  def request[T](verb: Verbs, apiPath: String, params: String = "")(implicit r: Reads[T]): Future[Option[T]] = {
    /** *
      * val parameters: Map[String, String] = params match {
      * case Some(m) => m.filter(_._2.isDefined).mapValues(_.getOrElse("")).filter(!_._2.isEmpty)
      * case None => Map.empty
      * } ***/
    val effectiveUrl = s"${Constants.API_URL}$apiPath?"
    val headers = Map(
      "Authorization" -> Seq(s"Bearer $accessToken"),
      "Content-Type" -> Seq("application/json")
    )
    val request: Req = url(effectiveUrl)
      .setMethod(verb.label)
      .setHeaders(headers)
    val requestWithParams = if (verb.label == Verbs.GET.label) {
      request
    } else {
      request.setBody(params)
    }
    println(requestWithParams.url)
    Request.send[T](requestWithParams)
  }

  // CalendarList

  // TODO: https://developers.google.com/google-apps/calendar/v3/reference/calendarList?hl=ja

  def deleteCalendarList(calendarId: String): Future[Option[Seq[String]]] = {
    val apiPath: String = Methods.CALENDAR_LIST_WITH_ID format calendarId
    request[Seq[String]](Verbs.DELETE, apiPath)
  }

  // TODO: option params
  def getCalendarList(calendarId: String = "primary"): Future[Option[CalendarList]] = {
    val apiPath: String = Methods.CALENDAR_LIST_WITH_ID format calendarId
    request[CalendarList](Verbs.GET, apiPath)
  }

  // TODO: option params
  def insertCalendarList(id: String, defaultReminders: Seq[DefaultReminders], notificationSettings: NotificationSettings): Future[Option[CalendarList]] = {
    val apiPath: String = Methods.CALENDAR_LIST
    val params: String =
      s"""
           {
             "defaultReminders": ${Json.toJson(defaultReminders)}
             ,
             "id": "$id",
             "notificationSettings": {
               "notifications": ${Json.toJson(notificationSettings.notifications)}
             }
           }
        """
    request[CalendarList](Verbs.POST, apiPath, params)
  }

  // TODO: option params
  def listCalendarList(): Future[Option[CalendarListList]] = {
    val apiPath: String = Methods.CALENDAR_LIST
    request[CalendarListList](Verbs.GET, apiPath)
  }

  // TODO:  option params
  def patchCalendarList(calendarId: String, params: String): Future[Option[CalendarList]] = {
    val apiPath: String = Methods.CALENDAR_LIST_WITH_ID format calendarId
    request[CalendarList](Verbs.PATCH, apiPath, params)
  }

  // TODO: option params
  def updateCalendarList(calendarId: String, defaultReminders: Seq[DefaultReminders], notificationSettings: NotificationSettings): Future[Option[CalendarList]] = {
    val apiPath: String = Methods.CALENDAR_LIST_WITH_ID format calendarId
    val params: String =
      s"""
           {
             "defaultReminders": ${Json.toJson(defaultReminders)}
             ,
             "notificationSettings": {
               "notifications": ${Json.toJson(notificationSettings.notifications)}
             }
           }
        """
    request[CalendarList](Verbs.PUT, apiPath, params)
  }

  // TODO: i do not understand about watch method => https://developers.google.com/google-apps/calendar/v3/reference/calendarList/watch

  // Calendars

  def clearCalendars(calendarId: String = "primary") = {
    val apiPath: String = Methods.CALENDARS_WITH_ID format calendarId
    request[Seq[String]](Verbs.POST, apiPath)
  }

  
}