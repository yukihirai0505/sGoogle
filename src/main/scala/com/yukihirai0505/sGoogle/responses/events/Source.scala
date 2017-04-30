package com.yukihirai0505.sGoogle.responses.events

case class Source(
                   url: Option[String],
                   title: Option[String]
                 )

import play.api.libs.json.Json

object Source {
  implicit val SourceFormat = Json.format[Source]
}
