package com.generic.bank.parsing

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
    println(message)

    val currency = message.value.substring(0, 3)
    val value = message.value.substring(3).toDouble

    val sender = SenderBic(Bic(message.sender))
    val receiver = ReceiverBic(Bic(message.receiver))
    val amount = Amount(Amount.Value(value), Amount.Currency.withName(currency))
    Right(new FinancialMessage(sender, receiver, amount))
  }
}
