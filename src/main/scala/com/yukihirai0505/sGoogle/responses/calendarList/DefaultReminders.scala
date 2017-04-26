package com.yukihirai0505.sGoogle.responses.calendarList

case class DefaultReminders(
                             method: String,
                             minutes: Int
                           )

import play.api.libs.json.Json

object DefaultReminders {
  implicit val DefaultRemindersFormat = Json.format[DefaultReminders]
}