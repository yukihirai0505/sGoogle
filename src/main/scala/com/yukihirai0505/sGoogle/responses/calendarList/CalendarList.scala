package com.yukihirai0505.sGoogle.responses.calendarList

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
                  notificationSettings: NotificationSettings,
                  primary: Boolean,
                  deleted: Option[Boolean]
                )

import play.api.libs.json.Json

object CalendarList {
  implicit val CalendarListFormat = Json.format[CalendarList]
}