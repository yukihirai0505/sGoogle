package com.yukihirai0505.sGoogle.model

sealed abstract class NotificationsType(val label: String)

object NotificationsType {

  case object EVENT_CREATION extends NotificationsType("eventCreation")

  case object EVENT_CHANGE extends NotificationsType("eventChange")

  case object EVENT_CANCELLATION extends NotificationsType("eventCancellation")

  case object EVENT_RESPONSE extends NotificationsType("eventResponse")

  case object AGENDA extends NotificationsType("agenda")
}
