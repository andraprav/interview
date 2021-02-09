package com.generic.bank.util

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object JsonUtil {
  private val mapper = JsonMapper.builder()
    .addModule(DefaultScalaModule)
    .build()

  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(value: Map[Symbol, Any]): String = {
    toJson(value map { case (k, v) => k.name -> v })
  }

  def toJson(value: Any): String = {
    mapper.writeValueAsString(value)
  }

  def toMap[V](json: String): Map[String, V] = fromJson[Map[String, V]](json)

  def fromJson[T](json: String): T = {
    mapper.readValue(json, new TypeReference[T] {})
  }
}
