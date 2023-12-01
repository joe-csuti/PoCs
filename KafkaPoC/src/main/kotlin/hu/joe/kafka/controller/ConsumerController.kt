package hu.joe.kafka.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class ConsumerController {
    @MessageMapping("/consumerNotification")
    @SendTo("/topic/consumer")
    @Throws(Exception::class)
    fun sendMessage(message: String): String {
        return message;
    }

    @MessageMapping("/streamConsumerNotification")
    @SendTo("/topic/stream-consumer")
    @Throws(Exception::class)
    fun sendMessageToStream(message: String): String {
        return message;
    }

    @MessageMapping("/streamOutputConsumerNotification")
    @SendTo("/topic/stream-output-consumer")
    @Throws(Exception::class)
    fun sendMessageToStreamOutput(message: String): String {
        return message;
    }
}
