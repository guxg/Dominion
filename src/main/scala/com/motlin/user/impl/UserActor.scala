package com.motlin.user.impl

import collection.mutable.{Map => MMap}

import akka.actor.{ActorRef, Actor}
import com.motlin.user.messages.{JoinChatRoom, ChatRoomReply, CreateChatRoom}

class UserActor(userName: String) extends Actor
{
	val chatRooms = MMap[String, ActorRef]()

	protected def receive =
	{
		case CreateChatRoom(name) =>
			val chatRoom = Actor.actorOf(new ChatRoomActor(name))
			chatRooms(name) = chatRoom
			chatRoom ! JoinChatRoom(self)
			self.reply(ChatRoomReply.fromActorRef(chatRoom))
	}
}