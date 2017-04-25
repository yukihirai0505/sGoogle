package com.yukihirai0505.sGoogle

import com.ning.http.client.FluentCaseInsensitiveStringsMap
import com.yukihirai0505.sGoogle.http.Response
import com.yukihirai0505.sGoogle.model.{Constants, OAuthConstants, Scope}
import com.yukihirai0505.sGoogle.responses.auth._
import dispatch.Defaults._
import dispatch._
import play.api.libs.json.Json

import scala.collection.JavaConverters._

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
  def requestToken(clientId: String, clientSecret: String, redirectURI: String, code: String): Future[Response[Auth]] = {
    val params = Map(
      OAuthConstants.CLIENT_ID -> clientId,
      OAuthConstants.CLIENT_SECRET -> clientSecret,
      OAuthConstants.REDIRECT_URI -> redirectURI,
      OAuthConstants.CODE -> code
    )
    val request = url(Constants.ACCESS_TOKEN_ENDPOINT) << params
    Http(request).map { resp =>
      val response = resp.getResponseBody
      val headers = ningHeadersToMap(resp.getHeaders)
      if (resp.getStatusCode != 200) throw new Exception(response.toString)
      Json.parse(response).asOpt[OAuth] match {
        case Some(o: OAuth) => Response(Some(o), resp.getStatusCode, headers)
        case _ =>
          Response(None, resp.getStatusCode, headers)
      }
    }
  }

  private def ningHeadersToMap(headers: FluentCaseInsensitiveStringsMap) = {
    mapAsScalaMapConverter(headers).asScala.map(e => e._1 -> e._2.asScala).toMap
  }

}
