package chat.websocket

object Configuration {
    import com.typesafe.config.ConfigFactory

    private val config = ConfigFactory.load
    config.checkValid(ConfigFactory.defaultReference)

    lazy val host = config.getString("chat.host")
    lazy val portWs = config.getInt("chat.port.ws")
}
