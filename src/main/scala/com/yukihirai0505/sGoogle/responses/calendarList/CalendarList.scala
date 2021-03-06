package com.yukihirai0505.sGoogle.responses.calendarList

import com.yukihirai0505.sGoogle.responses.common.DefaultReminders
import play.api.libs.json.Json

case class CalendarList(
                         kind: String,
                         etag: String,
                         id: String,
                         summary: String,
                         description: Option[String],
                         location: Option[String],
                         timeZone: String,
                         summaryOverride: Option[String],
                         colorId: String,
                         backgroundColor: String,
                         foregroundColor: String,
                         hidden: Option[Boolean],
                         selected: Boolean,
                         accessRole: String,
                         defaultReminders: Seq[DefaultReminders],
                         notificationSettings: Option[NotificationSettings],
                         primary: Option[Boolean],
                         deleted: Option[Boolean]
                       )

object CalendarList {
  implicit val CalendarListFormat = Json.format[CalendarList]
}