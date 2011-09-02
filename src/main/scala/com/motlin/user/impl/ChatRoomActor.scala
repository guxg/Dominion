package com.motlin.user.impl

import collection.mutable.{Set => MSet}

import com.motlin.user.messages.JoinChatRoom

import akka.actor._

class ChatRoomActor(name: String) extends Actor
{
	val users = MSet[ActorRef]()

	protected def receive =
	{
		case JoinChatRoom(actor) =>
			users += actor
	}
}