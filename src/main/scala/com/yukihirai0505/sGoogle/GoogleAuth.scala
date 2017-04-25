package com.yukihirai0505.sGoogle

import com.yukihirai0505.sGoogle.http.{Request, Verbs}
import com.yukihirai0505.sGoogle.model.{Constants, OAuthConstants, Scope}
import com.yukihirai0505.sGoogle.responses.auth._
import dispatch._

class GoogleAuth {

  /**
   * Scope string which will be append to the URL.
   *
   * @param scopes         Scopes
   * @return               String
   */
  def setScopes(scopes: Seq[Scope]): String = {
    val scopeLabels: List[String] = scopes.map(s => s.label).toList
    if (scopes.nonEmpty) scopeLabels.mkString("scope=", "%20", "")
    else ""
  }

  /**
   * Create the URL to call when retrieving an access token.
   *
   * @param clientId       Client identifier. (You need to register this on facebook app)
   * @param redirectURI    URI which the response is sent to.
   * @param scopes         Require scope.
   */
  def authURL(clientId: String, redirectURI: String, scopes: Seq[Scope] = Seq()): String = {
    ( Constants.AUTHORIZE_URL format (clientId, redirectURI) ) + s"&${setScopes(scopes)}"
  }

  /**
   * Post request to exchange a authentication code against an access token.
   * Note that an authentication code is valid one time only.
   *
   * @param clientId     Client identifier. (You need to register this on facebook)
   * @param clientSecret Client secret. (You need to register this on Facebook)
   * @param redirectURI  URI which the response is sent to. (You need to register this on Facebook)
   * @param code         Authentication code. You can retrieve it via codeURL.
   * @return             Future of Response[Authentication]
   */
  def requestToken(code: String, clientId: String, clientSecret: String, redirectURI: String): Future[Option[OAuth]] = {
    val params = Map(
      OAuthConstants.CODE -> code,
      OAuthConstants.CLIENT_ID -> clientId,
      OAuthConstants.CLIENT_SECRET -> clientSecret,
      OAuthConstants.REDIRECT_URI -> redirectURI,
      OAuthConstants.GRANT_TYPE -> OAuthConstants.AUTHORIZATION_CODE
    )
    val request = url(Constants.ACCESS_TOKEN_ENDPOINT)
      .setMethod(Verbs.POST.label)
      .setHeader("Content-Type", "application/x-www-form-urlencoded") << params
    Request.send[OAuth](request)
  }

}
