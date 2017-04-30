package com.yukihirai0505.sGoogle.responses.events

case class Organizer(
                      id: Option[String],
                      email: Option[String],
                      displayName: Option[String],
                      self: Option[Boolean]
                    )

import play.api.libs.json.Json

object Organizer {
  implicit val OrganizerFormat = Json.format[Organizer]
}
