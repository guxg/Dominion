package com.motlin.chat.impl

import akka.actor.TypedActor
import com.motlin.chat.api.MessageLog
import akka.serialization.RemoteTypedActorSerialization


object MessageLogImpl
{
	def serialize(messageLog: MessageLog): MessageLog = new SerializationProxy(RemoteTypedActorSerialization.toBinary(messageLog))

	def deserialize(bytes: Array[Byte]): MessageLog = RemoteTypedActorSerialization.fromBinaryToRemoteTypedActorRef(bytes)

	case class SerializationProxy(bytes: Array[Byte]) extends MessageLog
	{
		def readResolve(): Object = deserialize(bytes)

		def messages = sys.error("Not implemented on serialization proxy")

		def add(message: Message) { sys.error("Not implemented on serialization proxy") }
	}
}

class MessageLogImpl extends TypedActor with MessageLog
{
	var messages = List[Message]()

	def add(message: Message)
	{
		messages ::= message
	}
}