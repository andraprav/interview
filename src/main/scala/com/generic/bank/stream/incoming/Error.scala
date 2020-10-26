package com.generic.bank.stream.incoming

sealed trait Error

object Error {

  case object DirectoryNotFound extends Error
  case class System(underlying: Throwable) extends Error

}
