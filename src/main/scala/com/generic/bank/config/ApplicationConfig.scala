package com.generic.bank.config

case class ApplicationConfig(messageFolder: ApplicationConfig.MessageFolder)

object ApplicationConfig {
  case class MessageFolder(value: String) extends AnyVal

}
