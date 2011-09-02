package com.motlin.chat.server

import akka.actor.Actor
import com.motlin.user.impl.LoginActor

object Main
{
	def main(args: Array[String])
	{
		val loginActor = Actor.actorOf[LoginActor]

		Actor.remote.start()
		Actor.remote.register("login", loginActor)
	}
}