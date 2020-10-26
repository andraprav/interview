package com.generic.bank.parsing

import java.io.File

import com.generic.bank.domain.FinancialMessage

trait MessageParser {

  def parse(file: File): Either[Error, FinancialMessage]

}
