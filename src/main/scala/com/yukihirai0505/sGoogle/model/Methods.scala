package com.yukihirai0505.sGoogle.model

/**
  * author Yuki Hirai on 2016/11/08.
  */
object Methods {
  // https://developers.google.com/google-apps/calendar/v3/reference/calendarList?hl=ja
  val CALENDAR_LIST = "/calendar/v3/users/me/calendarList"
  val CALENDAR_LIST_WITH_ID = s"$CALENDAR_LIST/%s"
  // https://developers.google.com/google-apps/calendar/v3/reference/calendars
  val CALENDARS_WITH_ID = "/calendar/v3/calendars/%s/clear"
}