package com.motlin.chat.impl

import collection.mutable.{Map => MMap}
import akka.actor.TypedActor
import com.motlin.chat.api.{MessageLog, ChatService, ChatRoom}
import com.motlin.user.api.User

class ChatServiceImpl extends TypedActor with ChatService
{
	val rooms = MMap[String, ChatRoom]()

	def joinRoom(roomName: String, user: User, messageLog: MessageLog) =
	{
		val room = rooms.get(roomName).getOrElse
		{
			val room = TypedActor.newInstance(classOf[ChatRoom], new ChatRoomImpl(roomName))
			rooms(roomName) = room
			room
		}

		val session = room.join(user, messageLog)
		session
	}
}