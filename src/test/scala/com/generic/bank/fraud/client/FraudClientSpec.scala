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
    val fraudClient = new DefaultFraudClient(actorSystemModule, fraudApi)
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
  }
}
