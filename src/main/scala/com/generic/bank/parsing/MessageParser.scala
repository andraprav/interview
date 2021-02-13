package com.generic.bank.parsing

import cats.implicits.catsSyntaxEitherId
import com.generic.bank.domain.FinancialMessage.{Amount, ReceiverBic, SenderBic}
import com.generic.bank.domain.{Bic, FinancialMessage}
import com.generic.bank.util.{FileReader, JsonUtil}

import java.io.File

trait MessageParser {
  def parse(file: File): Either[Error, FinancialMessage]
}

class JsonMessageParser extends MessageParser {
  override def parse(file: File): Either[Error, FinancialMessage] = {
    val json = FileReader.readFile(file)
    val message = JsonUtil.mapper.readValue(json, classOf[Message])

    val currencyValue = message.value.substring(0, 3)
    val amountValue = message.value.substring(3).toDouble

    val sender = SenderBic(Bic(message.sender))
    val receiver = ReceiverBic(Bic(message.receiver))
    val currency = Amount.Currency.withNameEither(currencyValue)
    currency match {
      case Right(value) => Right(new FinancialMessage(sender, receiver, Amount(Amount.Value(amountValue), value)))
      case Left(value) => Error.Illegal(value.notFoundName + " is not supported").asLeft
    }
  }
}
