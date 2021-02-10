package com.generic.bank.util

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}

object JsonUtil {
  val mapper: ObjectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
