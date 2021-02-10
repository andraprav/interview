package com.generic.bank.parsing

import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.{JsonProperty, JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "messageType")
@JsonSubTypes(
  Array(
    new Type(value = classOf[MT103], name = "MT103"),
    new Type(value = classOf[MT202], name = "MT202")))
sealed trait Message extends Serializable

object Message {}

final case class MT103(@JsonProperty(value = "33B") value: String) extends Message with Serializable

object MT103 {}

final case class MT202(@JsonProperty(value = "32A") value: String) extends Message with Serializable

object MT202 {}
