package com.motlin.user.impl

import scala.collection.mutable.{Map => MMap}

import com.motlin.user.messages.{LoginReply, Credential}

import akka.actor.{ActorRef, Actor}

class LoginActor extends Actor
{
	val credentials = MMap[String, String]()
	val loggedInUsers = MMap[String, ActorRef]()

	protected def receive: Receive =
	{
		case Credential(userName, password) =>
			val userActor = credentials.get(userName) match
			{
				case Some(`password`) => loggedInUsers.get(userName)
				case Some(_) => None
				case None =>
					val user = Actor.actorOf(new UserActor(userName))
					credentials(userName) = password
					loggedInUsers(userName) = user
					Some(user)
			}

			self.reply(LoginReply.fromActorRef(userActor))
	}
}