package com.yukihirai0505.sGoogle.responses.events

case class Events(
                   kind: Option[String],
                   etag: Option[String],
                   id: Option[String],
                   status: Option[String],
                   htmlLink: Option[String],
                   created: Option[String],
                   updated: Option[String],
                   summary: Option[String],
                   description: Option[String],
                   location: Option[String]//, FIXME: over 22 fields
//                   colorId: Option[String],
//                   creator: Option[Creator],
//                   organizer: Option[Organizer],
//                   start: Option[EventsDate],
//                   end: Option[EventsDate],
//                   endTimeUnspecified: Option[Boolean],
//                   recurrence: Option[Seq[String]],
//                   recurringEventId: Option[String],
//                   originalStartTime: Option[EventsDate],
//                   transparency: Option[String],
//                   visibility: Option[String],
//                   iCalUID: Option[String],
//                   sequence: Option[Int],
//                   attendees: Option[Seq[Attendee]],
//                   attendeesOmitted: Option[Boolean],
//                   hangoutLink: Option[String],
//                   gadget: Option[Gadget],
//                   anyoneCanAddSelf: Option[Boolean],
//                   guestsCanInviteOthers: Option[Boolean],
//                   guestsCanModify: Option[Boolean],
//                   guestsCanSeeOtherGuests: Option[Boolean],
//                   privateCopy: Option[Boolean],
//                   locked: Option[Boolean],
//                   source: Option[Source],
//                   attachments: Option[Seq[Attachment]]
                 )

import play.api.libs.json.Json

object Events {
  implicit val EventsFormat = Json.format[Events]
}