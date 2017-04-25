package com.yukihirai0505.sGoogle.model

/**
  * project constant
  *
  * author Yuki Hirai on 2016/11/08.
  */
object Constants {
  val OAUTH_URL: String = "https://accounts.google.com/o/oauth2/v2/auth"
  val AUTHORIZE_URL: String = s"$OAUTH_URL?client_id=%s&redirect_uri=%s&response_type=code"
  val API_URL = "https://www.googleapis.com"
  val ACCESS_TOKEN_ENDPOINT: String = s"$API_URL/oauth2/v4/token"
}