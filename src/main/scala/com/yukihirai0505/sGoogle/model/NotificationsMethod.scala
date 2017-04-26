package com.yukihirai0505.sGoogle.model

sealed abstract class NotificationsMethod(val label: String)

object NotificationsMethod {

  case object EMAIL extends NotificationsMethod("email")

  case object SMS extends NotificationsMethod("sms")

}
