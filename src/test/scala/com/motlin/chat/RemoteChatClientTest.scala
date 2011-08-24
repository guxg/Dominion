package com.motlin.chat

import api.{ChatRoom, MessageLog}
import impl.{ChatRoomImpl, Message, MessageLogImpl}
import org.junit.Test
import com.motlin.user.api.{User, LoginService}
import akka.actor.{TypedActor, Actor}

class RemoteChatClientTest
{
	@Test
	def smoke_test()
	{
		val loginService = Actor.remote.typedActorFor(classOf[LoginService], "login-service", "localhost", 4200)
		val user: User = loginService.login("user 1", "password").get

		val messageLog = TypedActor.newInstance(classOf[MessageLog], classOf[MessageLogImpl])
		val chatSession = user.joinChatRoom("room 1", MessageLogImpl.serialize(messageLog))
		chatSession.chat("hello 1")
	}
}