package com.yukihirai0505.sGoogle.responses.events

case class Gadget(
                   `type`: Option[String],
                   title: Option[String],
                   link: Option[String],
                   iconLink: Option[String],
                   width: Option[Int],
                   height: Option[String],
                   display: Option[String]
                 )

import play.api.libs.json.Json

object Gadget {
  implicit val GadgetFormat = Json.format[Gadget]
}