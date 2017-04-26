package com.yukihirai0505.sGoogle.responses.calendarList

case class DefaultReminders(
                             method: Option[String],
                             minutes: Option[Int]
                           )

import play.api.libs.json.Json

object DefaultReminders {
  implicit val DefaultRemindersFormat = Json.format[DefaultReminders]
}