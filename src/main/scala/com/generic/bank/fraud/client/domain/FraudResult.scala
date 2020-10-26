package com.generic.bank.fraud.client.domain

sealed trait FraudResult

object FraudResult {
  case object Fraud extends FraudResult
  case object NoFraud extends FraudResult
}
