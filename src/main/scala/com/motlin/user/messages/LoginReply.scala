package com.motlin.user.messages

import akka.serialization.RemoteActorSerialization
import akka.actor.ActorRef

object LoginReply
{
	def serialize(actorRef: ActorRef) =
		RemoteActorSerialization.toRemoteActorRefProtocol(actorRef).toByteArray

	def fromActorRef(actorRef: Option[ActorRef]) =
		new LoginReply(actorRef.map(serialize))

	def toUser(optionLoginReply: Any): ActorRef =
		optionLoginReply.asInstanceOf[LoginReply].toUser.get
}

case class LoginReply(serializedUser: Option[Array[Byte]])
{
	def toUser = serializedUser.map(RemoteActorSerialization.fromBinaryToRemoteActorRef)
}