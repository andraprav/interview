package com.generic.bank.parsing

import com.generic.bank.domain.FinancialMessage
import com.generic.bank.util.{FileReader, JsonUtil}

import java.io.File

trait MessageParser {
  def parse(file: File): Either[Error, FinancialMessage]
}

class JsonMessageParser extends MessageParser {
  override def parse(file: File): Either[Error, FinancialMessage] = {
    val json = FileReader.readFile(file)
    val financialMessage = JsonUtil.fromJson[FinancialMessage](json)
    Right(financialMessage)
  }
}
