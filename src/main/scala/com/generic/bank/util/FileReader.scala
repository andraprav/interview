package com.generic.bank.util

import java.io.File
import spray.json._

object FileReader {
  def readFile(file: File): String = {
    val source = scala.io.Source.fromFile(file)
    val lines = try source.mkString finally source.close()
    lines.parseJson.toString()
  }
}
