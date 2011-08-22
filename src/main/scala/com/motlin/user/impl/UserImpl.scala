package com.motlin.user.impl

import akka.actor.TypedActor
import com.motlin.user.api.User
import com.motlin.chat.api.{MessageLog, ChatService}

class UserImpl(val name: String, chatService: ChatService) extends TypedActor with User
{
	// TODO: inline?
	def joinChatRoom(roomName: String, messageLog: MessageLog) =
	{
		chatService.joinRoom(roomName, this, messageLog)
	}
}