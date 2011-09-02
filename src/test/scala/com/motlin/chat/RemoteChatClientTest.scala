package com.motlin.chat

import com.motlin.user.messages._

import akka.actor.Actor
import org.junit.Test
import com.motlin.user.impl.{LoggerActor, LoginActor}

class RemoteChatClientTest
{
	@Test
	def smoke_test()
	{
		//	val loginActor = Actor.remote.actorFor("login", "localhost", 4200)
		val loginActor = Actor.actorOf[LoginActor].start()
		val loginReply = loginActor !! Credential("user 1", "password")
		val user = LoginReply.toUser(loginReply.get)

		val logger1 = Actor.actorOf(new LoggerActor("logger 1"))

		val chatRoomName = "My Chat Room"
		user ! CreateChatRoom(chatRoomName, logger1)
		// val chatRoom = ChatRoomReply.toChatRoom(optionChatRoomReply.get)

		// user ! Chat(chatRoomName, "Hello 1")
	}
}