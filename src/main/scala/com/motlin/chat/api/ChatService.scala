package com.motlin.chat.api

import com.motlin.user.api.User

trait ChatService
{
	def joinRoom(roomName: String, user: User, messageLog: MessageLog): ChatSession
}