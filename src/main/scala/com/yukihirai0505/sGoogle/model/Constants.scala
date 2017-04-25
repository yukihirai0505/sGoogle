package com.yukihirai0505.sGoogle.model

/**
  * project constant
  *
  * author Yuki Hirai on 2016/11/08.
  */
object Constants {
  val BASE_URL: String = "https://accounts.google.com/o/oauth2"
  private val OAUTH_URL: String = s"$BASE_URL/v2/auth"
  val AUTHORIZE_URL: String = s"$OAUTH_URL?client_id=%s&redirect_uri=%s&response_type=code"
  val ACCESS_TOKEN_ENDPOINT: String = s"$OAUTH_URL/v4/token"

}