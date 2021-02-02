package com.generic.bank.parsing

import com.generic.bank.domain.FinancialMessage
import com.generic.bank.util.FileReader
import com.generic.bank.util.JsonUtil.mapper

import java.io.File

trait MessageParser {

  def parse(file: File): Either[Error, FinancialMessage] = {
    val json = FileReader.readFile(file)
    val financialMessage = mapper.readValue[FinancialMessage](json)
    Right(financialMessage)
  }
}
