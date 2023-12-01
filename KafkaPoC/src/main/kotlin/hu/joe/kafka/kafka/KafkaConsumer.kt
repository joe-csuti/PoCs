package hu.joe.kafka.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service


@Service
class KafkaConsumer(private val messagingTemplate: SimpMessagingTemplate) {
    companion object {
        private val log = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    @KafkaListener(topics = ["\${project.topic}"], groupId = "test_topic")
    fun consumeTopic(message: String) {
        log.debug("Message received: $message")
        messagingTemplate.convertAndSend("/topic/consumer", message)
    }

    @KafkaListener(topics = ["\${project.stream-topic}"], groupId = "test_stream_topic")
    fun consumeStreamTopic(message: String) {
        log.debug("Stream message received: $message")
        messagingTemplate.convertAndSend("/topic/stream-consumer", message)
    }

    @KafkaListener(topics = ["\${project.stream-output-topic}"], groupId = "test_stream_output_topic")
    fun consumeStreamOutputTopic(message: String) {
        log.debug("Stream output message received: $message")
        messagingTemplate.convertAndSend("/topic/stream-output-consumer", message)
    }
}
