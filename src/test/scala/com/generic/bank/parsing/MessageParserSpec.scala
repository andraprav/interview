package com.generic.bank.parsing

import cats.implicits.catsSyntaxEitherId
import com.generic.bank.domain.FinancialMessage.Amount.Currency.{CHF, EUR}
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
    val messageParser = new FinancialMessageParser

    "return financialMessage from mt103 message type" in {
      val path = getClass.getResource("/messages/mt103_1.json").getPath
      val file = new File(path)
      val sender = SenderBic(Bic("BE71096123456769"))
      val receiver = ReceiverBic(Bic("RO09BCYP0000001234567890"))
      val amount = Amount(Value(123), EUR)
      val financialMessage = new FinancialMessage(sender, receiver, amount).asRight

      val result = messageParser.parse(file)

      result shouldBe financialMessage
    }

    "return XXX is not supported" in {
      val path = getClass.getResource("/messages/mt103_3.json").getPath
      val file = new File(path)
      val error = Error.Illegal("XXX is not supported").asLeft

      val result = messageParser.parse(file)

      result shouldBe error
    }

    "return financialMessage from mt202 message type" in {
      val path = getClass.getResource("/messages/mt202_2.json").getPath
      val file = new File(path)
      val sender = SenderBic(Bic("BBRUBEBB"))
      val receiver = ReceiverBic(Bic("RNCBROBU"))
      val amount = Amount(Value(123), CHF)
      val financialMessage = new FinancialMessage(sender, receiver, amount).asRight

      val result = messageParser.parse(file)

      result shouldBe financialMessage
    }

    "return INCORRECT_FIELD not a valid field" in {
      val path = getClass.getResource("/messages/mt202_1.json").getPath
      val file = new File(path)
      val error = Error.Illegal("INCORRECT_FIELD not a valid field").asLeft

      val result = messageParser.parse(file)

      result shouldBe error
    }
  }

}
