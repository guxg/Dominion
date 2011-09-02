package com.motlin.user.impl

import akka.actor.Actor
import akka.event.EventHandler

class LoggerActor(name: String) extends Actor
{
	protected def receive =
	{
		case string: String =>
			EventHandler.info(this, name + ": " + string)
	}
}