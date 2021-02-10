package com.generic.bank.parsing

import com.generic.bank.domain.FinancialMessage.Amount.Currency.EUR
import com.generic.bank.domain.FinancialMessage.Amount.Value
import com.generic.bank.domain.FinancialMessage.{Amount, ReceiverBic, SenderBic}
import com.generic.bank.domain.{Bic, FinancialMessage}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.io.File

class MessageParserSpec
  extends AnyWordSpec
    with Matchers
    with ScalaCheckPropertyChecks {

  "MessageParser#parse" should {

    val messageParser = new JsonMessageParser

    "return financialMessage" in {

      val path = getClass.getResource("/messages/mt103_1.json").getPath
      val file = new File(path)
      val sender = SenderBic(Bic("BE71096123456769"))
      val receiver = ReceiverBic(Bic("RO09BCYP0000001234567890"))
      val amount = Amount(Value(123), EUR)
      val financialMessage = new FinancialMessage(sender, receiver, amount)

      val result = messageParser.parse(file)

      result shouldBe Right(financialMessage)

    }
  }

}
