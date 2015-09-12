package chat.websocket

import spray.can.websocket.WebSocketServerWorker
import akka.actor.ActorRef
import spray.routing.HttpServiceActor

class WebSocketWorker(client: ActorRef, id: String) extends WebSocketServerWorker {
    import akka.contrib.pattern.DistributedPubSubExtension
    import akka.contrib.pattern.DistributedPubSubMediator.Subscribe
    import akka.event.LoggingReceive
    import spray.can.websocket
    import spray.can.websocket.frame.{TextFrame, BinaryFrame, CloseFrame}
    import spray.can.websocket.FrameCommandFailed
    import spray.http.HttpRequest
    import spray.can.websocket.frame.StatusCode._
    import akka.contrib.pattern.DistributedPubSubMediator.Publish
    import spray.can.Http
    import Push._

    val mediator = DistributedPubSubExtension(context.system).mediator

    override def receive = preRoute orElse handshaking orElse closeLogic

    var mytopic: Option[String] = None
    lazy val topic = mytopic.getOrElse("public")

    override val serverConnection = client
    override def businessLogic: Receive = LoggingReceive {
        case p: Push => send(TextFrame(p.toString))
        case websocket.UpgradedToWebSocket =>
            mediator ! Publish(topic, PushHasArrived(id))
            mediator ! Subscribe(topic, self)
        case x: BinaryFrame => send(CloseFrame(UnsupportedData))
        case TextFrame(text) =>
            mediator ! Publish(topic, Push(id, text.decodeString("UTF8")))
        case CloseFrame(code, msg) =>
            log.info("closeframe: " + msg, code.code)
            mediator ! Publish(topic, PushIsGone(id))
        case x: FrameCommandFailed =>
            log.error("frame command failed", x)
            //mediator ! Publish(topic, Push(id, "hiccup"))
        case ev: Http.ConnectionClosed =>
            mediator ! Publish(topic, PushIsGone(id))
            closeLogic(ev)
    }

    def preRoute: Receive = LoggingReceive {
        case x: HttpRequest =>
            val mytopic = (x.uri.path match {
                case p if p.startsWithSlash && p.length > 1 => Some(p.dropChars(1).head.toString)
                case p if p.startsWithSegment => Some(p.head.toString)
                case _ => None
            }).filterNot(_.contains("/"))
            handshaking(x)
    }
}

object WebSocketWorker {
    import akka.actor.Props
    def props(serverConnection: ActorRef, id: String) = Props(classOf[WebSocketWorker], serverConnection, id)
}