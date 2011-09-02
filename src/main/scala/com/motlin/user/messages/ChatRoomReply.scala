package com.motlin.user.messages

import akka.serialization.RemoteActorSerialization
import akka.actor.ActorRef

object ChatRoomReply
{
	def serialize(actorRef: ActorRef) =
		RemoteActorSerialization.toRemoteActorRefProtocol(actorRef).toByteArray

	def fromActorRef(actorRef: ActorRef) =
		new ChatRoomReply(serialize(actorRef))

	def toChatRoom(optionChatRoom: Any): ActorRef =
		optionChatRoom.asInstanceOf[ChatRoomReply].toChatRoom
}

case class ChatRoomReply(serializedChatRoom: Array[Byte])
{
	def toChatRoom = RemoteActorSerialization.fromBinaryToRemoteActorRef(serializedChatRoom)
}