package com.yukihirai0505.sGoogle.responses.auth

case class OAuth(
                  accessToken: String,
                  idToken: String,
                  expiresIn: Long,
                  tokenType: String,
                  refreshToken: Option[String]
                )

import com.github.tototoshi.play.json.JsonNaming
import play.api.libs.json.Json

object OAuth {
  implicit val OAuthFormat = JsonNaming.snakecase(Json.format[OAuth])
}