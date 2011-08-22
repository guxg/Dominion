package com.motlin.chat.impl

import akka.actor.TypedActor
import com.motlin.user.api.User
import com.motlin.chat.api.{ChatRoom, ChatSession}

class ChatSessionImpl(user: User, chatRoom: ChatRoom) extends TypedActor with ChatSession
{
	def chat(message: String)
	{
		chatRoom.chat(Message(user.name, message))
	}
}