package hu.joe.kafka.config

import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams

val STRING_SERDE: Serde<String?>? = Serdes.String()

@Configuration
@EnableKafkaStreams
class KafkaConfig {
    @Value("\${project.stream-topic}")
    private lateinit var streamTopicName: String

    @Value("\${project.stream-output-topic}")
    private lateinit var streamOutputTopicName: String

    @Bean
    fun kStream(streamsBuilder: StreamsBuilder): KStream<String?, String?>? {
        val stream = streamsBuilder.stream<String?, String?>(streamTopicName, Consumed.with(STRING_SERDE, STRING_SERDE))
        stream
                .filter { _, value -> value != null && value.contains("9") }
                .to(streamOutputTopicName)
        return stream
    }
}
