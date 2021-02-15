package com.generic.bank.fraud.client

import com.generic.bank.domain.FinancialMessage
import com.generic.bank.fraud.api.DefaultFraudApi
import com.generic.bank.fraud.client.domain.FraudResult
import com.generic.bank.modules.ActorSystemModule
import com.softwaremill.quicklens.ModifyPimp
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import scala.concurrent.ExecutionContext

class FraudClientSpec
  extends AnyWordSpec
    with Matchers
    with ScalaCheckPropertyChecks {

  import com.generic.bank.arbitraries.ArbitraryFinancialMessage._

  "FraudClient#call" should {
    val actorSystemModule = ActorSystemModule()
    val fraudApi = new DefaultFraudApi(actorSystemModule)
    val fraudMapper = new FraudMapper
    val fraudClient = new DefaultFraudClient(actorSystemModule, fraudApi, fraudMapper)
    implicit val executionSystem: ExecutionContext = actorSystemModule.executionContext

    "return Fraud" in {
      forAll { (financialMessage: FinancialMessage) =>
        val fraudMessage = financialMessage
          .modify(_.amount.currency)
          .setTo(FinancialMessage.Amount.Currency.CAD)
        for {
          res <- fraudClient.call(fraudMessage)
        } yield {
          res shouldBe FraudResult.Fraud
        }
      }
    }

    "return NoFraud" in {
      forAll { (financialMessage: FinancialMessage) =>
        val noFraudMessage = financialMessage
          .modify(_.amount.currency)
          .setTo(FinancialMessage.Amount.Currency.EUR)
        for {
          res <- fraudClient.call(noFraudMessage)
        } yield {
          res shouldBe FraudResult.NoFraud
        }
      }
    }

    "return an Error.Illegal" in {
      forAll { (financialMessage: FinancialMessage) =>
        val CHFMessage = financialMessage
          .modify(_.amount.currency)
          .setTo(FinancialMessage.Amount.Currency.CHF)
        for {
          res <- fraudClient.call(CHFMessage)
        } yield {
          res.swap.getOrElse(null) shouldBe a[Error.Illegal]
        }
      }
    }

    "return an Error.System" in {
      forAll { (financialMessage: FinancialMessage) =>
        val JPYMessage = financialMessage
          .modify(_.amount.currency)
          .setTo(FinancialMessage.Amount.Currency.JPY)
        for {
          res <- fraudClient.call(JPYMessage)
        } yield {
          res.swap.getOrElse(null) shouldBe a[Error.System]
        }
      }
    }

    "return Future.Failure" in {
      forAll { (financialMessage: FinancialMessage) =>
        val AUDMessage = financialMessage
          .modify(_.amount.currency)
          .setTo(FinancialMessage.Amount.Currency.AUD)
        for {
          res <- fraudClient.call(AUDMessage)
        } yield {
          res.swap.getOrElse(null) shouldBe a[RuntimeException]
        }
      }
    }
  }
}
