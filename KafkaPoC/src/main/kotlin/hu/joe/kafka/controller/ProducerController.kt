package hu.joe.kafka.controller

import hu.joe.kafka.kafka.KafkaProducer
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.table.api.Schema
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment
import org.apache.flink.types.Row
import org.apache.flink.util.CloseableIterator
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.support.KafkaHeaders.GROUP_ID
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api")
class ProducerController(
        private val producer: KafkaProducer,
) {
    @Value("\${project.topic}")
    private lateinit var topicName: String

    @Value("\${project.stream-topic}")
    private lateinit var streamTopicName: String

    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var kafkaServers: String

    @PostMapping("/send-sync", consumes = ["*/*"])
    fun sendSync(@RequestBody message: String) {
        producer.send(topicName, toJson(message))
    }

    @PostMapping("/send-async", consumes = ["*/*"])
    fun sendAsync(@RequestBody message: String) {
        producer.sendAsync(topicName, toJson(message))
    }

    @PostMapping("/send-stream", consumes = ["*/*"])
    fun sendAStream(@RequestBody message: String) {
        producer.sendAsync(streamTopicName, toJson(message))
    }

    private fun toJson(message: String): String {
        return "{message: \"$message\"}"
    }

    @PostMapping("/select", consumes = ["*/*"], produces = ["application/json"])
    fun select(@RequestBody message: String): List<String> {
        val source: KafkaSource<String> = KafkaSource.builder<String>()
                .setBootstrapServers(kafkaServers)
                .setTopics(streamTopicName)
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StringDeserializer::class.java))
                .setGroupId(GROUP_ID)
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setBounded(OffsetsInitializer.latest()) //Ha ez nincs beállítva akkor a selectResult feldolgozás nem lép ki, így is lehet pipe-ot építeni
                .build()
        val env = StreamExecutionEnvironment.getExecutionEnvironment()
        val stream: DataStream<String> = env
                .fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source")
        val tableEnv = StreamTableEnvironment.create(env)

        val schema: Schema = Schema.newBuilder()
                .columnByExpression("message", "f0")
                .primaryKey("f0")
                .build()
        tableEnv.createTemporaryView("myTable", stream, schema)

        val selectResult: CloseableIterator<Row>? = tableEnv.executeSql("SELECT message FROM myTable WHERE message LIKE '%$message%'").collect()
        selectResult.use {
            return selectResult!!
                    .asSequence()
                    .map<Row, String> { row -> row.getFieldAs(0) }
                    .toList()
        }
    }
}
