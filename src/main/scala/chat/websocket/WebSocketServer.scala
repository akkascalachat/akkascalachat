package chat.websocket

import akka.actor.{ActorLogging, Actor}

class WebSocketServer extends Actor with ActorLogging {
    import spray.can.Http

    def receive = {
        case Http.Connected(remoteAddress, localAddress) =>
            val client = sender()
            val conn = context.actorOf(WebSocketWorker.props(client, remoteAddress.toString))
            client ! Http.Register(conn)
    }
}

object WebSocketServer {
    import akka.actor.Props
    def props() = Props(classOf[WebSocketServer])
}
