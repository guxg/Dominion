package com.motlin.chat

import impl.{Message, MessageLogAdapter}
import org.junit.Test
import akka.actor.Actor
import com.motlin.user.api.LoginService

class RemoteChatClientTest
{
	@Test
	def smoke_test()
	{
		val loginService = Actor.remote.typedActorFor(classOf[LoginService], "login-service", "localhost", 4200)
		val user = loginService.login("user 1", "password").get
//		val chatSession = user.joinChatRoom("room 1", new MessageLogAdapter
//		{
//			var messages = List[Message]()
//			def add(message: Message) { messages ::= message }
//		})
//		chatSession.chat("hello 1")
	}
}