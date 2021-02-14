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
sealed trait Message extends Serializable {
  val value: String
  val sender: String
  val receiver: String
}

object Message {}

final case class MT103(@JsonProperty(value = "33B") value: String,
                       @JsonProperty(value = "50A") sender: String,
                       @JsonProperty(value = "51A") senderInstitution: String,
                       @JsonProperty(value = "57A") receiverInstitution: String,
                       @JsonProperty(value = "59A") receiver: String) extends Message with Serializable

object MT103 {}

final case class MT202(@JsonProperty(value = "32A") value: String,
                       @JsonProperty(value = "52A") sender: String,
                       @JsonProperty(value = "58A") receiver: String) extends Message with Serializable

object MT202 {}
