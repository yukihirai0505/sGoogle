package com.yukihirai0505.sGoogle

import play.api.libs.json.{Json, Reads}

import com.yukihirai0505.com.scala.Request
import com.yukihirai0505.com.scala.constants.Verbs
import com.yukihirai0505.sGoogle.model.{Constants, Methods}
import com.yukihirai0505.sGoogle.responses.calendarList.{CalendarList, CalendarListList, NotificationSettings}
import com.yukihirai0505.sGoogle.responses.calendars.Calendars
import com.yukihirai0505.sGoogle.responses.common.DefaultReminders
import com.yukihirai0505.sGoogle.responses.events.EventsList
import dispatch._

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
    val effectiveUrl = s"${Constants.API_URL}$apiPath?"
    val headers = Map(
      "Authorization" -> Seq(s"Bearer $accessToken"),
      "Content-Type" -> Seq("application/json")
    )
    val request: Req = url(effectiveUrl)
      .setMethod(verb.label)
      .setHeaders(headers)
    val requestWithParams = if (verb.label == Verbs.GET.label) request else request.setBody(params)
    println(requestWithParams.url)
    Request.send[T](requestWithParams)
  }

  // CalendarList

  // TODO: https://developers.google.com/google-apps/calendar/v3/reference/calendarList?hl=ja

  def deleteCalendarList(calendarId: String): Future[Option[String]] = {
    val apiPath: String = Methods.CALENDAR_LIST_WITH_ID format calendarId
    request[String](Verbs.DELETE, apiPath)
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

  def clearCalendars(calendarId: String = "primary"): Future[Option[String]] = {
    val apiPath: String = Methods.CALENDARS_CLEAR format calendarId
    request[String](Verbs.POST, apiPath)
  }

  def deleteCalendars(secondaryCalendarId: String): Future[Option[String]] = {
    val apiPath: String = Methods.CALENDARS_WITH_ID format secondaryCalendarId
    request[String](Verbs.DELETE, apiPath)
  }

  def getCalendars(calendarId: String): Future[Option[Calendars]] = {
    val apiPath: String = Methods.CALENDARS_WITH_ID format calendarId
    request[Calendars](Verbs.GET, apiPath)
  }

  // TODO: option params
  def insertCalendars(summary: String): Future[Option[Calendars]] = {
    val apiPath: String = Methods.CALENDARS
    val params: String = {
      s"""
          {
            "summary": "$summary"
          }
       """
    }
    request[Calendars](Verbs.POST, apiPath, params)
  }

  def patchCalendars(calendarId: String, calendars: Calendars) = {
    val apiPath = Methods.CALENDARS_WITH_ID format calendarId
    val params = Json.toJson(calendars).toString
    request[Calendars](Verbs.PATCH, apiPath, params)
  }

  def updateCalendars(calendarId: String, calendars: Calendars) = {
    val apiPath = Methods.CALENDARS_WITH_ID format calendarId
    val params = Json.toJson(calendars).toString
    request[Calendars](Verbs.PUT, apiPath, params)
  }

  // Events

  def listEvents(calendarId: String) = {
    val apiPath = Methods.EVENTS_WITH_ID format calendarId
    request[EventsList](Verbs.GET, apiPath)
  }

}