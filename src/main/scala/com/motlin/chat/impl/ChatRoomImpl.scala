package com.motlin.chat.impl

import collection.mutable.{Map => MMap}
import akka.actor.TypedActor
import com.motlin.user.api.User
import com.motlin.chat.api.{ChatSession, MessageLog, ChatRoom}

class ChatRoomImpl(name: String) extends TypedActor with ChatRoom
{
	val sessions = MMap[User, MessageLog]()
	var messages = List[Message]()

	def join(user: User, messageLog: MessageLog) =
	{
		require(!sessions.contains(user))

		sessions(user) = messageLog
		messages.foreach(messageLog.add)

		val session = TypedActor.newInstance(classOf[ChatSession], new ChatSessionImpl(user, this))
		session
	}

	def chat(message: Message)
	{
		messages ::= message
		sessions.values.foreach(_.add(message))
	}
}