package com.yukihirai0505.sGoogle.responses.calendarList

case class Notifications(
                          `type`: String,
                          method : String
                        )

import play.api.libs.json.Json

object Notifications {
  implicit val NotificationsFormat = Json.format[Notifications]
}
