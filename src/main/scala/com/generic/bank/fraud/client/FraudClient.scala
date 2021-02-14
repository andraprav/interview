package com.generic.bank.fraud.client

import cats.implicits.catsSyntaxEitherId
import com.generic.bank.domain.FinancialMessage
import com.generic.bank.fraud.api.domain.FraudResult.{Fraud => ApiFraud, NoFraud => ApiNoFraud}
import com.generic.bank.fraud.api.domain.{FraudResult => ApiFraudResult}
import com.generic.bank.fraud.api.{FraudApi, Error => ApiError}
import com.generic.bank.fraud.client.domain.FraudResult
import com.generic.bank.modules.ActorSystemModule

import scala.concurrent.{ExecutionContext, Future}

trait FraudClient {
  def call(financialMessage: FinancialMessage): Future[Either[Error, FraudResult]]
}

class DefaultFraudClient(actorSystemModule: ActorSystemModule, fraudApi: FraudApi) extends FraudClient {

  implicit private val executionSystem: ExecutionContext = actorSystemModule.executionContext

  override def call(financialMessage: FinancialMessage): Future[Either[Error, FraudResult]] = {
    fraudApi.handle(financialMessage).map(mapEither)
  }

  def mapEither(either: Either[ApiError, ApiFraudResult]): Either[Error, FraudResult] = {
    either match {
      case Left(apiError: ApiError) => mapError(apiError).asLeft
      case Right(fraudResult: ApiFraudResult) => mapFraudResult(fraudResult).asRight
    }
  }

  def mapError(error: ApiError): Error = {
    error match {
      case ApiError.Illegal(description) => Error.Illegal(description)
      case ApiError.System(underlying) => Error.System(underlying)
    }
  }

  def mapFraudResult(fraudResult: ApiFraudResult): FraudResult = {
    fraudResult match {
      case ApiFraud => FraudResult.Fraud
      case ApiNoFraud => FraudResult.NoFraud
    }
  }
}
