package com.motlin.dominion.net.client

import com.google.inject.Inject
import actors.Actor
import java.net.Socket
import com.motlin.dominion.net.comm._
import actors.threadpool.{TimeUnit, Executors}
import org.slf4j.LoggerFactory
import com.motlin.dominion.net.{NamedThreadFactory, SocketInputHandler, SocketOutputHandler}

object ServerConnection
{
	val LOGGER = LoggerFactory.getLogger(classOf[ServerConnection])
}

case class ServerConnection @Inject() (serverSocket: Socket, socketOutputHandler: SocketOutputHandler) extends Actor
{
	val socketInputHandler = new SocketInputHandler(serverSocket)
	{
		def handle(readObject: AnyRef)
		{
			ServerConnection.this ! readObject
		}
	}

	val executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory(this.getClass.getName))
	executorService.execute(socketInputHandler)

	socketOutputHandler.start

	def act()
	{
		var pongs = 0

		loop
		{
			react
			{
				case Welcome =>
				{
					ServerConnection.LOGGER.info("Got welcome message. Logging in.")
					socketOutputHandler ! Login("testuser")
				}
				case LoggedIn(true) =>
				{
					ServerConnection.LOGGER.info("Logged in successfully. Sending ping.")
					socketOutputHandler ! Ping
				}
				case Pong =>
				{
					pongs += 1
					ServerConnection.LOGGER.info("Got {} pongs. Sending ping.", pongs)
					socketOutputHandler ! Ping
					if (pongs >= 10)
					{
						socketOutputHandler ! Close
						socketInputHandler.cleanUp()
						executorService.shutdown()
						executorService.awaitTermination(10L, TimeUnit.SECONDS)
						this.exit()
					}
				}
				case other =>
				{
					ServerConnection.LOGGER.info("Unexpected message: {}", other.toString)
				}
			}
		}
	}
}