package com.yukihirai0505.sGoogle.responses.events

import com.yukihirai0505.sGoogle.responses.common.DefaultReminders


case class EventsList(
                       kind: Option[String],
                       etag: Option[String],
                       summary: Option[String],
                       description: Option[String],
                       updated: Option[String],
                       timeZone: Option[String],
                       accessRole: Option[String],
                       defaultReminders: DefaultReminders,
                       nextPageToken: Option[String],
                       nextSyncToken: Option[String],
                       items: Option[Seq[Events]]
                     )

import play.api.libs.json.Json

object EventsList {
  implicit val EventsListFormat = Json.format[EventsList]
}