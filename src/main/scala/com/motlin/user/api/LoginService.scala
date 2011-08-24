package com.motlin.user.api

trait LoginService
{
	def login(userName: String, password: String): Option[Array[Byte]]
}