package com.generic.bank.config

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ConfigSpec extends AnyWordSpecLike with Matchers {
  import ConfigSpec._

  "Configuration" can {

    "ApplicationConfig" should {

      "be correctly parsed" in {

        val result = Config.get.get

        result shouldBe applicationConfig

      }
    }
  }
}

object ConfigSpec {

  val applicationConfig: ApplicationConfig = ApplicationConfig(
    messageFolder = ApplicationConfig.MessageFolder("/messages")
  )

}
