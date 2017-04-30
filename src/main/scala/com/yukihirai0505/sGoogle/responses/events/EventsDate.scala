package com.yukihirai0505.sGoogle.responses.events

case class EventsDate(
                       date: Option[String],
                       dateTime: Option[String],
                       timeZone: Option[String]
                     )

import play.api.libs.json.Json

object EventsDate {
  implicit val EventsDateFormat = Json.format[EventsDate]
}