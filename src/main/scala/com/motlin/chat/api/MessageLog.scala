package com.motlin.chat.api

import com.motlin.chat.impl.Message

trait MessageLog
{
	def messages: List[Message]
	def add(message: Message)
}