package com.motlin.user.impl

import scala.collection.mutable.{Map => MMap}
import com.motlin.user.api.{User, LoginService}
import com.motlin.chat.api.ChatService
import akka.actor.TypedActor
import akka.serialization.TypedActorSerialization

class LoginServiceImpl(chatService: ChatService) extends TypedActor with LoginService
{
	val credentials = MMap[String, String]()
	val loggedInUsers = MMap[String, User]()

	def serializeUserImpl(user: User) = TypedActorSerialization.toBinaryJ(user, UserImplFormat)

	def login(userName: String, password: String) =
	{
		credentials.get(userName) match
		{
			case Some(`password`) => loggedInUsers.get(userName).map(serializeUserImpl)
			case Some(_) => None
			case None =>
			{
				val user = TypedActor.newInstance(classOf[User], new UserImpl(userName, chatService))
				credentials(userName) = password
				loggedInUsers(userName) = user
				Some(serializeUserImpl(user))
			}
		}
	}
}