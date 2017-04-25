package com.yukihirai0505.sGoogle

import com.yukihirai0505.sGoogle.model.Scope
import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source
import scala.language.postfixOps
import scala.util.Try

/**
  * Created by k00068 on 2017/04/25.
  */
class GoogleSpec extends FlatSpec with Matchers {

  private def readTestDataFromConfig(): Map[String, String] = {
    Try {
      val testDataFile = Source.fromURL(getClass.getResource("/testData.txt")).mkString
      val testData = testDataFile.split(",").toSeq
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
  val scopes = Seq(
    Scope.OPEN_ID,
    Scope.EMAIL,
    Scope.CALENDAR_API
  )


  "Google Auth url" should "return a valid authorization url" in {
    val authUrl = auth.authURL(clientId, redirectUri, scopes)
    println(authUrl)
    assert(authUrl.nonEmpty)
  }

}
