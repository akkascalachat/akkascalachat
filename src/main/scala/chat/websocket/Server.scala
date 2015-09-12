package chat.websocket

object Server extends App {

    import akka.actor.ActorSystem
    import akka.io.IO
    import spray.can.Http
    import spray.can.server.UHttp


    def contextize() {
        import system.dispatcher

        implicit lazy val system = ActorSystem("websocket-actors")
        val wssserver = system.actorOf(WebSocketServer.props())
        IO(UHttp) ! Http.Bind(wssserver, Configuration.host, Configuration.portWs)

        sys.addShutdownHook({ IO(UHttp) ! Http.Unbind; system.shutdown })
    }
    contextize()
}
