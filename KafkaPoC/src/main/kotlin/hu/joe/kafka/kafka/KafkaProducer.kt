package hu.joe.kafka.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer


@Service
class KafkaProducer(
        private val kafkaAdmin: KafkaAdmin,
        private val kafkaTemplate: KafkaTemplate<String, String>,
) {
    companion object {
        private val log = LoggerFactory.getLogger(KafkaProducer::class.java)
        private val defaultCallback: BiConsumer<in SendResult<String, String>, in Throwable> = BiConsumer { result: SendResult<String, String>, ex: Throwable? ->
            if (ex == null)
                log.debug("Sent message with offset=[" + result.recordMetadata.offset() + "]")
            else
                log.error("Unable to send message", ex)
        }
    }

    fun send(topicName: String, message: String) {
        log.debug(kafkaAdmin.clusterId())
        kafkaTemplate.send(topicName, message).get()
    }

    fun sendAsync(topicName: String, message: String, callback: BiConsumer<in SendResult<String, String>, in Throwable> = defaultCallback) {
        log.debug(kafkaAdmin.clusterId())
        val future: CompletableFuture<SendResult<String, String>> = kafkaTemplate.send(topicName, message)
        future.whenComplete(callback)
    }
}
