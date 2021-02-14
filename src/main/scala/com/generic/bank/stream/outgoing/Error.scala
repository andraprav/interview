package com.generic.bank.stream.outgoing


sealed trait Error

object Error {

  object ClientError extends Error

  object ParsingError extends Error

}
