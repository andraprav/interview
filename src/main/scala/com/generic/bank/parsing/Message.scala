package com.generic.bank.parsing

import com.fasterxml.jackson.annotation.{JsonProperty, JsonSubTypes, JsonTypeInfo}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "messageType")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[MT103], name = "MT103"),
    new JsonSubTypes.Type(value = classOf[MT202], name = "MT202")))
sealed trait Message

final case class MT103(@JsonProperty(value = "33B") value: String) extends Message

final case class MT202(@JsonProperty(value = "32A") value: String) extends Message
