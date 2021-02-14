package com.generic.bank.parsing

import cats.implicits.catsSyntaxEitherId
import com.generic.bank.domain.FinancialMessage.{Amount, ReceiverBic, SenderBic}
import com.generic.bank.domain.{Bic, FinancialMessage}
import com.generic.bank.util.FileReader
import com.generic.bank.util.JsonUtil.readValue

import java.io.File

trait MessageParser {
  def parse(file: File): Either[Error, FinancialMessage]
}

class FinancialMessageParser extends MessageParser {
  private val currencyLength = 3;

  override def parse(file: File): Either[Error, FinancialMessage] = {
    val json = FileReader.readFile(file)
    val messageOption = readValue(json)

    messageOption match {
      case Right(message) =>
        val currencyValue = message.value.substring(0, currencyLength)
        val amountValue = message.value.substring(currencyLength).toDouble

        val sender = SenderBic(Bic(message.sender))
        val receiver = ReceiverBic(Bic(message.receiver))
        val currency = Amount.Currency.withNameEither(currencyValue)
        currency match {
          case Right(value) => Right(new FinancialMessage(sender, receiver, Amount(Amount.Value(amountValue), value)))
          case Left(value) => Error.Illegal(value.notFoundName + " is not supported").asLeft
        }
      case Left(e) => e.asLeft
    }
  }
}
