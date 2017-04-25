package com.yukihirai0505.sGoogle.model

/**
  * author Yuki Hirai on 2016/11/11.
  */
sealed abstract class Scope(val label: String)
object Scope {
  case object GMAIL_API extends Scope("https://mail.google.com/")
  case object GOOGLE_ANALYTICS_API extends Scope("https://www.googleapis.com/auth/analytics")
  case object CALENDAR_API extends Scope("https://www.googleapis.com/auth/calendar")
  case object YOUTUBE_DATA_API extends Scope("https://www.googleapis.com/auth/youtube")
  case object SITES_API extends Scope("https://sites.google.com/feeds/")
  case object PROFILE extends Scope("profile")
  case object EMAIL extends Scope("email")
  case object OPEN_ID extends Scope("openid")
}