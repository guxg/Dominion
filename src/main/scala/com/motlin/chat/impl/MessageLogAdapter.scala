package com.motlin.chat.impl

import akka.actor.TypedActor
import com.motlin.chat.api.MessageLog

abstract class MessageLogAdapter extends TypedActor with MessageLog
{
}