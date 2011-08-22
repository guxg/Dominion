package com.motlin.user.api

import com.motlin.chat.api.{ChatSession, MessageLog}

trait User
{
	def name: String

	def joinChatRoom(roomName: String, messageLog: MessageLog): ChatSession
}