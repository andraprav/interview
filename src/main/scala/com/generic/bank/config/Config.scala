package com.generic.bank.config

import pureconfig.ConfigSource
import pureconfig.generic.auto._

import scala.util.Try

object Config {

  def get: Try[ApplicationConfig] =
    Try(
      ConfigSource.default.at("app").loadOrThrow[ApplicationConfig]
    )

}
