package hu.joe.kafka

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.transaction.annotation.EnableTransactionManagement

private val log = LoggerFactory.getLogger(Application::class.java)
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
    log.info("Working directory: {}", System.getProperty("user.dir"))
}

@SuppressWarnings(value = ["PMD", "checkstyle:hideutilityclassconstructor"])
@SpringBootApplication
@EnableTransactionManagement
@ConfigurationPropertiesScan(value = ["hu.joe.kafka"])
class Application
