package com.motlin.chat.server

import com.motlin.chat.api.ChatService
import com.motlin.chat.impl.ChatServiceImpl
import com.motlin.user.api.LoginService
import com.motlin.user.impl.LoginServiceImpl
import akka.actor.{Actor, TypedActor}

object Main
{
	def main(args: Array[String])
	{
		val chatService = TypedActor.newInstance(classOf[ChatService], classOf[ChatServiceImpl])
		val loginService = TypedActor.newInstance(classOf[LoginService], new LoginServiceImpl(chatService))

		Actor.remote.start()
		Actor.remote.registerTypedActor("login-service", loginService)
	}
}