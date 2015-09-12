package chat.websocket

sealed case class Push(id: String, msg: String)  {
    override def toString(): String = id + ":" + msg
}
object Push {
    def PushIsGone(id1: String) = new Push(id1 ," passed out!")
    def PushHasArrived(id1: String) = new Push(id1, " joined the party!")
}
