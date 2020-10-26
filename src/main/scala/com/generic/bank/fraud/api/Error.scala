package com.generic.bank.fraud.api

/** Do not change this code :)
  */
sealed trait Error

object Error {

  case class Illegal(description: String) extends Error
  case class System(underlying: Throwable) extends Error

}
