package com.yukihirai0505.sGoogle.responses.calendars

case class Calendars(
                         kind: Option[String],
                         etag: Option[String],
                         id: Option[String],
                         summary: Option[String],
                         description: Option[String],
                         location: Option[String],
                         timeZone: Option[String]
                       )

import play.api.libs.json.Json

object Calendars {
  implicit val CalendarsFormat = Json.format[Calendars]
}