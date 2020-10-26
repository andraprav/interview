package com.generic.bank.fraud.api.domain

/** Do not change this code :)
  */
sealed trait FraudResult

object FraudResult {
  case object Fraud extends FraudResult
  case object NoFraud extends FraudResult
}
