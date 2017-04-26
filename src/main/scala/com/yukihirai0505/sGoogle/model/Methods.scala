package com.yukihirai0505.sGoogle.model

/**
  * author Yuki Hirai on 2016/11/08.
  */
object Methods {
  // https://developers.google.com/google-apps/calendar/v3/reference/calendarList/delete?hl=ja
  val CALENDAR_LIST_DELETE = "/calendar/v3/users/me/calendarList/%s"
  val CALENDAR_LIST_GET = "/calendar/v3/users/me/calendarList/%s"
  val CALENDAR_LIST = "/calendar/v3/users/me/calendarList"
}