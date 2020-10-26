package com.generic.bank.modules

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

case class ActorSystemModule(actorSystem: ActorSystem) {
  val executionContext: ExecutionContext = actorSystem.dispatcher
}

object ActorSystemModule {

  def apply(): ActorSystemModule =
    ActorSystemModule(
      actorSystem = ActorSystem("interview")
    )

}
