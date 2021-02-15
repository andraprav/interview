package com.generic.bank.fraud.client

import com.generic.bank.domain.FinancialMessage
import com.generic.bank.fraud.api.FraudApi
import com.generic.bank.fraud.client.domain.FraudResult
import com.generic.bank.modules.ActorSystemModule

import scala.concurrent.{ExecutionContext, Future}

trait FraudClient {
  def call(financialMessage: FinancialMessage): Future[Either[Error, FraudResult]]
}

class DefaultFraudClient(actorSystemModule: ActorSystemModule, fraudApi: FraudApi, fraudMapper: FraudMapper) extends FraudClient {

  implicit private val executionSystem: ExecutionContext = actorSystemModule.executionContext

  override def call(financialMessage: FinancialMessage): Future[Either[Error, FraudResult]] = {
    fraudApi.handle(financialMessage).map(fraudMapper.mapEither)
  }
}
