package com.generic.bank

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import com.generic.bank.config.ApplicationConfig
import com.generic.bank.domain.FinancialMessage
import com.generic.bank.modules.ActorSystemModule
import com.generic.bank.stream.incoming.IncomingStream
import com.generic.bank.stream.outgoing.{Error, OutgoingStream}

import scala.concurrent.{ExecutionContext, Future}

object Main extends App {

  private val actorSystemModule = ActorSystemModule()
  implicit private val actorSystem: ActorSystem = actorSystemModule.actorSystem
  implicit private val ec: ExecutionContext = actorSystemModule.executionContext

  val applicationConfig = ApplicationConfig(ApplicationConfig.MessageFolder("/messages"))
  val incomingStream = new IncomingStream(applicationConfig)
  val source = incomingStream.source().getOrElse(null)

  val outgoingStream = OutgoingStream
  val output = outgoingStream.process(source)
  val sink = Sink.foreach[Future[Either[Error, FinancialMessage]]](financialMessage =>
    financialMessage.foreach {
      case Right(value) => println(value)
      case _ =>
    })

  output.runWith(sink)

}
