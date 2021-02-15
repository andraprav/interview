package com.generic.bank.fraud.client

import cats.implicits.catsSyntaxEitherId
import com.generic.bank.fraud.api.domain.FraudResult.{Fraud => ApiFraud, NoFraud => ApiNoFraud}
import com.generic.bank.fraud.api.domain.{FraudResult => ApiFraudResult}
import com.generic.bank.fraud.api.{Error => ApiError}
import com.generic.bank.fraud.client.domain.FraudResult

class FraudMapper {

  def mapEither(either: Either[ApiError, ApiFraudResult]): Either[Error, FraudResult] = {
    either match {
      case Left(apiError: ApiError) => mapError(apiError).asLeft
      case Right(fraudResult: ApiFraudResult) => mapFraudResult(fraudResult).asRight
    }
  }

  private def mapError(error: ApiError): Error = {
    error match {
      case ApiError.Illegal(description) => Error.Illegal(description)
      case ApiError.System(underlying) => Error.System(underlying)
    }
  }

  private def mapFraudResult(fraudResult: ApiFraudResult): FraudResult = {
    fraudResult match {
      case ApiFraud => FraudResult.Fraud
      case ApiNoFraud => FraudResult.NoFraud
    }
  }
}
