package com.yukihirai0505.sGoogle.responses.events

case class Attendee(
                     id: Option[String],
                     email: Option[String],
                     displayName: Option[String],
                     organizer: Option[Boolean],
                     self: Option[Boolean],
                     resource: Option[Boolean],
                     optional: Option[Boolean],
                     responseStatus: Option[String],
                     comment: Option[String],
                     additionalGuests: Option[Int]
                   )

import play.api.libs.json.Json

object Attendee {
  implicit val AttendeeFormat = Json.format[Attendee]
}
