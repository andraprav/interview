package com.generic.bank.stream.outgoing

import akka.NotUsed
import akka.stream.scaladsl.Source
import cats.implicits.catsSyntaxEitherId
import com.generic.bank.domain.FinancialMessage
import com.generic.bank.fraud.api.DefaultFraudApi
import com.generic.bank.fraud.client.DefaultFraudClient
import com.generic.bank.fraud.client.domain.FraudResult.Fraud
import com.generic.bank.modules.ActorSystemModule
import com.generic.bank.parsing.FinancialMessageParser

import java.io.File
import scala.concurrent.{ExecutionContext, Future}

trait OutgoingStream {

}

object OutgoingStream {
  private val actorSystemModule = ActorSystemModule()
  implicit val executionSystem: ExecutionContext = actorSystemModule.executionContext

  private val messageParser = new FinancialMessageParser
  private val fraudApi = new DefaultFraudApi(actorSystemModule)
  private val fraudClient = new DefaultFraudClient(actorSystemModule, fraudApi)

  def process(source: Source[File, NotUsed]): Source[Future[Either[Error, FinancialMessage]], NotUsed] = {
    val outgoingStream = source.map(file => {
      val financialMessageEither = messageParser.parse(file)
      val financialMessage = financialMessageEither match {
        case Left(_) => Future(Error.ParsingError.asLeft)
        case Right(value) => getFraud(value)
      }
      financialMessage
    })
    outgoingStream
  }

  private def getFraud(financialMessage: FinancialMessage): Future[Either[Error, FinancialMessage]] = {
    val isFraudEither = fraudClient.call(financialMessage)
    isFraudEither.map {
      case Left(_) => Error.ClientError.asLeft
      case Right(fraudResult) => financialMessage.copy(isFraud = Some(fraudResult.equals(Fraud))).asRight
    }
  }
}
