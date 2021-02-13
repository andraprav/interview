package com.generic.bank.parsing

sealed trait Error

object Error {

  case class Illegal(description: String) extends Error

}