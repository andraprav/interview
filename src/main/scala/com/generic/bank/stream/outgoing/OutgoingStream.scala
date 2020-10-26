package com.generic.bank.stream.outgoing

import java.io.File

import akka.NotUsed
import akka.stream.scaladsl.Source

trait OutgoingStream {

  def process(source: Source[File, NotUsed]): Nothing = {
    val _ = source
    ???
  }

}
