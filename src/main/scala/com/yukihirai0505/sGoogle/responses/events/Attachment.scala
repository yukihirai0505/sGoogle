package com.yukihirai0505.sGoogle.responses.events

case class Attachment(
                       fileUrl: Option[String],
                       title: Option[String],
                       mimeType: Option[String],
                       iconLink: Option[String],
                       fileId: Option[String]
                     )

import play.api.libs.json.Json

object Attachment {
  implicit val AttachmentFormat = Json.format[Attachment]
}
