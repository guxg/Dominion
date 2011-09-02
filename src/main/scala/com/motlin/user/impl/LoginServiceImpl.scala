package com.motlin.user.impl

import scala.collection.mutable.{Map => MMap}
import com.motlin.user.api.{User, LoginService}
import com.motlin.chat.api.ChatService
import akka.serialization.{Serializer, SerializerBasedActorFormat, TypedActorSerialization}
import com.motlin.user.impl.LoginServiceImpl.SerializationProxy
import akka.actor.{Actor, TypedActor}


object LoginServiceImpl
{
	object LoginServiceImplFormat extends SerializerBasedActorFormat[LoginServiceImpl]
	{
		val serializer = Serializer.Java
	}

	def serialize(loginService: LoginService) = TypedActorSerialization.toBinaryJ(loginService, LoginServiceImplFormat)

	def deserialize(bytes: Array[Byte]) = TypedActorSerialization.fromBinaryJ(bytes, LoginServiceImplFormat)

	case class SerializationProxy(bytes: Array[Byte])
	{
		def readResolve(): Object = deserialize(bytes)
	}
}

class LoginServiceImpl(chatService: ChatService) extends TypedActor with LoginService
{
	val credentials = MMap[String, String]()
	val loggedInUsers = MMap[String, User]()

	def login(userName: String, password: String) =
	{
		credentials.get(userName) match
		{
			case Some(`password`) => loggedInUsers.get(userName).map(UserImpl.serialize)
			case Some(_) => None
			case None =>
			{
				val user = TypedActor.newInstance(classOf[User], new UserImpl(userName, chatService))
				credentials(userName) = password
				loggedInUsers(userName) = user
				Some(UserImpl.serialize(user))
			}
		}
	}

	def writeReplace(): Object = new SerializationProxy(LoginServiceImpl.serialize(this))
}