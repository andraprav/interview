package com.generic.bank.fraud.client

import com.generic.bank.domain.FinancialMessage
import com.generic.bank.fraud.client.domain.FraudResult

import scala.concurrent.Future

trait FraudClient {

  def call(financialMessage: FinancialMessage): Future[Either[Error, FraudResult]]

}
