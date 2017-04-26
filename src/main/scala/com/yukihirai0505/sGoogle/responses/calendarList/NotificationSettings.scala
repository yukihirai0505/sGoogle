package com.yukihirai0505.sGoogle.responses.calendarList

case class NotificationSettings(
                                 notifications: Seq[Notifications]
                               )

import play.api.libs.json.Json

object NotificationSettings {
  implicit val NotificationSettingsFormat = Json.format[NotificationSettings]
}