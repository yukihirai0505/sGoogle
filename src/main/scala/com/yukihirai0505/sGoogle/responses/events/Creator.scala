package com.yukihirai0505.sGoogle.responses.events

case class Creator(
                    id: Option[String],
                    email: Option[String],
                    displayName: Option[String],
                    self: Option[Boolean]
                  )

import play.api.libs.json.Json

object Creator {
  implicit val CreatorFormat = Json.format[Creator]
}
