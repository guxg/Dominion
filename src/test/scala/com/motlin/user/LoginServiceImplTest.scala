package com.motlin.user

import akka.actor.TypedActor
import org.junit.{Assert, Test}

import api.LoginService
import impl.LoginServiceImpl

class LoginServiceImplTest
{
	val loginService = TypedActor.newInstance(classOf[LoginService], classOf[LoginServiceImpl])

	@Test
	def smoke_test()
	{
		Assert.assertEquals("Craig", loginService.login("Craig", "password").get.name)
	}

	@Test
	def wrong_password()
	{
		Assert.assertEquals("Craig", loginService.login("Craig", "password").get.name)
		Assert.assertEquals(None, loginService.login("Craig", "wrong password"))
	}
	
	@Test
	def right_password()
	{
		Assert.assertEquals("Craig", loginService.login("Craig", "password").get.name)
		Assert.assertEquals("Craig", loginService.login("Craig", "password").get.name)
	}
}