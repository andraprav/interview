package com.generic.bank.fraud.client

sealed trait Error

object Error {

  case class Illegal(description: String) extends Error

  case class System(underlying: Throwable) extends Error

}
