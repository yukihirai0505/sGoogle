package com.yukihirai0505.sGoogle.responses.calendarList

case class CalendarListList(
                             kind: String,
                             etag: String,
                             nextPageToken: Option[String],
                             nextSyncToken: String,
                             items: Seq[CalendarList]
                           )

import play.api.libs.json.Json

object CalendarListList {
  implicit val CalendarListListFormat = Json.format[CalendarListList]
}