package com.motlin.user.impl

import akka.actor.TypedActor
import com.motlin.user.api.User
import com.motlin.chat.api.{MessageLog, ChatService}
import akka.serialization.RemoteTypedActorSerialization

object UserImpl
{
	def serialize(user: User): User = new SerializationProxy(RemoteTypedActorSerialization.toBinary(user))

	def deserialize(bytes: Array[Byte]): User = RemoteTypedActorSerialization.fromBinaryToRemoteTypedActorRef(bytes)

	case class SerializationProxy(bytes: Array[Byte]) extends User
	{
		def readResolve(): Object = deserialize(bytes)

		def name = sys.error("Not implemented on serialization proxy")

		def joinChatRoom(roomName: String, messageLog: MessageLog) = sys.error("Not implemented on serialization proxy")
	}
}

class UserImpl(val name: String, chatService: ChatService) extends TypedActor with User
{
	// TODO: inline?
	def joinChatRoom(roomName: String, messageLog: MessageLog) =
	{
		chatService.joinRoom(roomName, this, messageLog)
	}
}
