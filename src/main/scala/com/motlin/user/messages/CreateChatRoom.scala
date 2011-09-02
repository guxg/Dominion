package com.motlin.user.messages

import akka.actor.ActorRef
import akka.serialization.RemoteActorSerialization


object ChatRoomReply
{

	def toChatRoom(optionChatRoom: Any): ActorRef =
		optionChatRoom.asInstanceOf[ChatRoomReply].toChatRoom
}

trait ActorCompanion[T <: ActorUtil]
{
	def create(serializedActor: Array[Byte]): T

	def fromActorRef(actorRef: ActorRef) =
	{
		create(serialize(actorRef))
	}

	def serialize(actorRef: ActorRef) =
		RemoteActorSerialization.toRemoteActorRefProtocol(actorRef).toByteArray
}

object CreateChatRoom extends ActorCompanion[CreateChatRoom]
{

}

trait ActorUtil[T <: ActorUtil]
{
	def companion: ActorCompanion[T]

	def serializedActor: Array[Byte]
	def deserialize = RemoteActorSerialization.fromBinaryToRemoteActorRef(serializedActor)
}

case class CreateChatRoom(chatRoomName: String, serializedActor: Array[Byte]) extends ActorUtil[CreateChatRoom]
{
	override def companion: ActorCompanion[CreateChatRoom] = CreateChatRoom
}