package com.motlin.chat

import akka.actor.TypedActor
import api.{ChatService, MessageLog}
import com.motlin.user.api.LoginService
import com.motlin.user.impl.LoginServiceImpl
import impl.{ChatServiceImpl, Message, MessageLogAdapter}
import org.junit.{Assert, Test}

class ChatServiceImplTest
{
	val chatService = TypedActor.newInstance(classOf[ChatService], classOf[ChatServiceImpl])
	val loginService = TypedActor.newInstance(classOf[LoginService], new LoginServiceImpl(chatService))

	@Test
	def smoke_test()
	{
		def createMessageLog(userName: String, message: String) =
		{
			val user = loginService.login(userName, "password").get
			val messageLog = TypedActor.newInstance(classOf[MessageLog], new MessageLogAdapter()
			{
				var messages: List[Message] = List()

				def add(message: Message)
				{
					messages ::= message
				}
			})
			val chatRoom = user.joinChatRoom("test room", messageLog)
			chatRoom.chat(message)
			messageLog
		}

		val messageLog1 = createMessageLog("user 1", "Hello 1")
		val messageLog2 = createMessageLog("user 2", "Hello 2")

		Assert.assertEquals(List(Message("user 2", "Hello 2"), Message("user 1", "Hello 1")), messageLog1.messages)
		Assert.assertEquals(List(Message("user 2", "Hello 2"), Message("user 1", "Hello 1")), messageLog2.messages)
	}

}