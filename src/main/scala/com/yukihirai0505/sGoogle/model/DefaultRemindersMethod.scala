package com.yukihirai0505.sGoogle.model

sealed abstract class DefaultRemindersMethod(val label: String)

object DefaultRemindersMethod {

  case object EMAIL extends DefaultRemindersMethod("email")

  case object SMS extends DefaultRemindersMethod("sms")

  case object POPUP extends DefaultRemindersMethod("popup")
}