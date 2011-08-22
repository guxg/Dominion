package com.motlin.user.api

import akka.actor.TypedActor

trait LoginService
{
	def login(userName: String, password: String): Option[User]
}