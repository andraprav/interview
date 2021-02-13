package com.generic.bank.util

import cats.implicits.catsSyntaxEitherId
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.generic.bank.parsing.{Error, Message}

object JsonUtil {
  val mapper: ObjectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY)

  def readValue(json: String): Either[Error, Message] = {
    try {
      JsonUtil.mapper.readValue(json, classOf[Message]).asRight
    } catch {
      case e: UnrecognizedPropertyException => Error.Illegal(e.getPropertyName + " not a valid field").asLeft
    }
  }
}
