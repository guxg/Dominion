package com.motlin.chat.api

import com.motlin.user.api.User
import com.motlin.chat.impl.Message

trait ChatRoom
{
	def join(user: User, messageLog: MessageLog): ChatSession

	def chat(message: Message)
}