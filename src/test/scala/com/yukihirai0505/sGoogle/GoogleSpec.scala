package com.yukihirai0505.sGoogle

import com.yukihirai0505.sGoogle.model.Scope
import com.yukihirai0505.sGoogle.responses.auth.OAuth
import helpers.WebHelper
import org.openqa.selenium.By
import org.scalatest.matchers.{BePropertyMatchResult, BePropertyMatcher}
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source
import scala.language.postfixOps
import scala.util.Try

/**
  * Created by yuky on 2017/04/25.
  */
class GoogleSpec extends FlatSpec with Matchers with WebHelper {

  private def anInstanceOf[T](implicit tag: reflect.ClassTag[T]) = {
    val clazz = tag.runtimeClass.asInstanceOf[Class[T]]
    new BePropertyMatcher[AnyRef] {
      def apply(left: AnyRef) =
        BePropertyMatchResult(left.getClass.isAssignableFrom(clazz), "an instance of " + clazz.getName)
    }
  }

  private def readTestDataFromConfig(): Map[String, String] = {
    Try {
      val testDataFile = Source.fromURL(getClass.getResource("/testData.txt")).mkString
      val testData = testDataFile.split("\n").toSeq
      Map(testData map { a =>
        val data = a.split("=")
        data(0) -> data(1)
      }: _*)
    }.getOrElse(throw new Exception(
      "Please provide a valid access_token by making a token.txt in resources.\n" +
        "See testData.txt.default for detail."
    ))
  }

  val auth = new GoogleAuth
  val testData: Map[String, String] = readTestDataFromConfig()
  val clientId: String = testData.getOrElse("clientId", "")
  val clientSecret: String = testData.getOrElse("clientSecret", "")
  val redirectUri: String = testData.getOrElse("redirectUri", "")
  val googleAccountId: String = testData.getOrElse("googleAccountId", "")
  val googleAccountPassword: String = testData.getOrElse("password", "")
  val scopes = Seq(
    Scope.OPEN_ID,
    Scope.EMAIL,
    Scope.CALENDAR_API
  )
  var authUrl = ""
  var code = ""

  "Google Auth url" should "return a valid authorization url" in {
    authUrl = auth.authURL(clientId, redirectUri, scopes)
    assert(authUrl.nonEmpty)
  }

  "Request Auth url" should "return a valid code" in {
    val identifierIdKey = "identifierId"
    val passwordKey = "password"
    val nextClass = "CwaK9"

    open(authUrl)

    waitId(identifierIdKey)
    findElementById(identifierIdKey).sendKeys(googleAccountId)
    findElementByClassName(nextClass).click()

    waitName(passwordKey)
    findElementByName(passwordKey).sendKeys(googleAccountPassword)
    findElementByClassName(nextClass).click()

    waitUrlContains("code")
    code = "code=[^&]+".r.findFirstIn(currentUrl).getOrElse("").split("=")(1)
    assert(code.nonEmpty)
  }

  "Request AccessToken" should "return accessToken" in {
    val request = Await.result(auth.requestToken(code, clientId, clientSecret, redirectUri), Duration.Inf)
    request.foreach(v => println(v.accessToken))
    request should be(anInstanceOf[Some[OAuth]])
  }

}
