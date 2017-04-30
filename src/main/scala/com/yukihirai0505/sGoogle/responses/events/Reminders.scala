package com.yukihirai0505.sGoogle.responses.events

import com.yukihirai0505.sGoogle.responses.common.DefaultReminders
import play.api.libs.json.Json

case class Reminders(
                      useDefault: Option[Boolean],
                      overrides: Option[Seq[DefaultReminders]]
                    )

object Reminders {
  implicit val RemindersFormat = Json.format[Reminders]
}
