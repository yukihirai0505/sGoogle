package com.yukihirai0505.sGoogle

import com.yukihirai0505.sGoogle.model.{DefaultRemindersMethod, NotificationsMethod, NotificationsType, Scope}
import com.yukihirai0505.sGoogle.responses.auth.OAuth
import com.yukihirai0505.sGoogle.responses.calendarList._
import com.yukihirai0505.sGoogle.responses.calendars.Calendars
import com.yukihirai0505.sGoogle.responses.common.DefaultReminders
import com.yukihirai0505.sGoogle.responses.events.Events
import helpers.WebHelper
import org.scalatest.matchers.{BePropertyMatchResult, BePropertyMatcher}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source
import scala.language.postfixOps
import scala.util.Try

/**
  * Created by yuky on 2017/04/25.
  */
class GoogleSpec extends FlatSpec with Matchers with WebHelper {

  private def anInstanceOf[T](implicit tag: reflect.ClassTag[T]) = {
    val clazz = tag.runtimeClass.asInstanceOf[Class[T]]
    new BePropertyMatcher[AnyRef] {
      def apply(left: AnyRef) =
        BePropertyMatchResult(left.getClass.isAssignableFrom(clazz), "an instance of " + clazz.getName)
    }
  }

  private def readTestDataFromConfig(): Map[String, String] = {
    Try {
      val testDataFile = Source.fromURL(getClass.getResource("/testData.txt")).mkString
      val testData = testDataFile.split("\n").toSeq
      Map(testData map { a =>
        val data = a.split("=")
        data(0) -> data(1)
      }: _*)
    }.getOrElse(throw new Exception(
      "Please provide a valid access_token by making a token.txt in resources.\n" +
        "See testData.txt.default for detail."
    ))
  }

  val auth = new GoogleAuth
  val testData: Map[String, String] = readTestDataFromConfig()
  val clientId: String = testData.getOrElse("clientId", "")
  val clientSecret: String = testData.getOrElse("clientSecret", "")
  val redirectUri: String = testData.getOrElse("redirectUri", "")
  val gmailId: String = testData.getOrElse("gmailId", "")
  val gmailPassword: String = testData.getOrElse("gmailPassword", "")
  val testCalendarId: String = testData.getOrElse("testCalendarId", "")
  val scopes = Seq(
    Scope.OPEN_ID,
    Scope.EMAIL,
    Scope.CALENDAR_API
  )
  var authUrl = ""
  var code = ""
  var accessToken = ""

  "Google Auth url" should "return a valid authorization url" in {
    authUrl = auth.authURL(clientId, redirectUri, scopes)
    println(s"authUrl: $authUrl")
    assert(authUrl.nonEmpty)
  }

  "Request Auth url" should "return a valid code" in {
    val identifierIdKey = "identifierId"
    val passwordKey = "password"
    val nextClass = "CwaK9"

    open(authUrl)

    waitId(identifierIdKey)
    findElementById(identifierIdKey).sendKeys(gmailId)
    findElementByClassName(nextClass).click()

    waitName(passwordKey)
    findElementByName(passwordKey).sendKeys(gmailPassword)
    findElementByClassName(nextClass).click()

    waitUrlContains("code")
    code = "code=[^&]+".r.findFirstIn(currentUrl).getOrElse("").split("=")(1)
    println(s"code: $code")
    assert(code.nonEmpty)
  }

  "Request AccessToken" should "return accessToken" in {
    val request = Await.result(auth.requestToken(code, clientId, clientSecret, redirectUri), Duration.Inf)
    request.foreach { v =>
      accessToken = v.accessToken
      println(s"accessToken: ${v.accessToken}")
    }
    request should be(anInstanceOf[Some[OAuth]])
  }

  "deleteCalendarList" should "return empty" in {
    val request = Await.result(new Google(accessToken).deleteCalendarList(testCalendarId), Duration.Inf)
    assert(request.isEmpty)
  }

  "insertCalendarList" should "return a Some[CalendarList]" in {
    val defaultReminders =
      Seq(
        DefaultReminders(Some(DefaultRemindersMethod.EMAIL.label), Some(0))
      )
    val notificationSettings = NotificationSettings(
      Seq(
        Notifications(method = NotificationsMethod.EMAIL.label, `type` = NotificationsType.EVENT_CREATION.label)
      )
    )
    val request = Await.result(new Google(accessToken).insertCalendarList(testCalendarId, defaultReminders, notificationSettings), Duration.Inf)
    request.foreach(v => println(v.summary))
    request should be(anInstanceOf[Some[CalendarList]])
  }


  "getCalendarList" should "return a Some[CalendarList]" in {
    val request = Await.result(new Google(accessToken).getCalendarList(), Duration.Inf)
    request.foreach(v => println(v.summary))
    request should be(anInstanceOf[Some[CalendarList]])
  }

  "getCalendarListList" should "return a Some[CalendarListList]" in {
    val request = Await.result(new Google(accessToken).listCalendarList(), Duration.Inf)
    request.foreach(v => v.items.foreach(v => println(v.summary)))
    request should be(anInstanceOf[Some[CalendarListList]])
  }

  "patchCalendarList" should "return a Some[CalendarList]" in {
    val params = """{ "summaryOverride": "hogehoge" }"""
    val request = Await.result(new Google(accessToken).patchCalendarList(testCalendarId, params), Duration.Inf)
    request should be(anInstanceOf[Some[CalendarList]])
  }

  "updateCalendarList" should "return a Some[CalendarList]" in {
    val defaultReminders =
      Seq(
        DefaultReminders(Some(DefaultRemindersMethod.EMAIL.label), Some(0))
      )
    val notificationSettings = NotificationSettings(
      Seq(
        Notifications(method = NotificationsMethod.EMAIL.label, `type` = NotificationsType.EVENT_CREATION.label),
        Notifications(method = NotificationsMethod.EMAIL.label, `type` = NotificationsType.EVENT_CHANGE.label),
        Notifications(method = NotificationsMethod.EMAIL.label, `type` = NotificationsType.EVENT_CANCELLATION.label),
        Notifications(method = NotificationsMethod.EMAIL.label, `type` = NotificationsType.EVENT_RESPONSE.label),
        Notifications(method = NotificationsMethod.EMAIL.label, `type` = NotificationsType.AGENDA.label)
      )
    )
    val request = Await.result(new Google(accessToken).updateCalendarList(testCalendarId, defaultReminders, notificationSettings), Duration.Inf)
    request should be(anInstanceOf[Some[CalendarList]])
  }

  // dangerous => clear all primary calendar events
  /***
  "clearCalendars" should "return empty" in {
    val request = Await.result(new Google(accessToken).clearCalendars(testCalendarId), Duration.Inf)
    assert(request.isEmpty)
  }***/

  var secondaryCalendarId = ""

  "insertCalendars" should "return Some[Calendars]" in {
    val summary = "fugafuga"
    val request = Await.result(new Google(accessToken).insertCalendars(summary), Duration.Inf)
    secondaryCalendarId = request.fold("")(v => v.id.getOrElse(""))
    request should be(anInstanceOf[Some[Calendars]])
  }

  "getCalendars" should "return Some[Calendars]" in {
    val request = Await.result(new Google(accessToken).getCalendars(secondaryCalendarId), Duration.Inf)
    request should be(anInstanceOf[Some[Calendars]])
  }

  "updateCalendars" should "return Some[Calendars]" in {
    val calendars = Calendars(
      kind = None,
      etag = None,
      id = None,
      summary = Some("fugafuga2"),
      description = None,
      location = None,
      timeZone = None
    )
    val request = Await.result(new Google(accessToken).updateCalendars(secondaryCalendarId, calendars), Duration.Inf)
    request.flatMap(_.summary) should be(Some("fugafuga2"))
    request should be(anInstanceOf[Some[Calendars]])
  }

  "patchCalendars" should "return Some[Calendars]" in {
    val calendars = Calendars(
      kind = None,
      etag = None,
      id = None,
      summary = None,
      description = None,
      location = None,
      timeZone = Some("Europe/Zurich")
    )
    val request = Await.result(new Google(accessToken).patchCalendars(secondaryCalendarId, calendars), Duration.Inf)
    request.flatMap(_.timeZone) should be(Some("Europe/Zurich"))
    request should be(anInstanceOf[Some[Calendars]])
  }

  "deleteCalendars" should "return empty" in {
    val request = Await.result(new Google(accessToken).deleteCalendars(secondaryCalendarId), Duration.Inf)
    assert(request.isEmpty)
  }

  "listEvents" should "return Some[EventList]" in {
    val request = Await.result(new Google(accessToken).listEvents(testCalendarId), Duration.Inf)
    request should be(anInstanceOf[Some[Events]])
  }
}
