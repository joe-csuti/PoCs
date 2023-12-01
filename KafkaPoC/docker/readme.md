WithZookepper:
    Felépítjük a clustert:
        docker-compose up -d
    Létrehozunk topicokat:
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --create --topic test_topic --partitions 1 --replication-factor 2
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --create --topic test_stream_topic --partitions 1 --replication-factor 2
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --create --topic test_stream_output_topic --partitions 1 --replication-factor 2
    Topic módosítása:
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --alter --topic test_topic --partitions 2 --replication-factor 2
    Topic törlése 
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --delete --topic test_topic
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --delete --topic test_stream_topic
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --delete --topic test_stream_output_topic
    Listázás
        docker exec -it  kafka_node_1 kafka-topics --bootstrap-server kafka_node_1:9092 --list
        Felületről (config):
            http://localhost:22000/editor/data?connection=KAFKAZOOKEEPER&path=%2Fconfig%2Ftopics
    Elindítani egy console-os consumert (és mindent olvasni az elejétől)
        docker exec -it  kafka_node_1 kafka-console-consumer --bootstrap-server kafka_node_1:9092 --topic test_topic --from-beginning --max-messages 100
        docker exec -it  kafka_node_1 kafka-console-consumer --bootstrap-server kafka_node_1:9092 --topic test_topic
    Törölni mindent    
        docker-compose rm -svf
    Újraindítani    
        docker-compose restart

WithoutZookepper:
    Felépítjük a clustert:
        docker-compose up -d
    Létrehozunk topicokat:
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --create --topic test_topic --partitions 1 --replication-factor 2
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --create --topic test_stream_topic --partitions 1 --replication-factor 2
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --create --topic test_stream_output_topic --partitions 1 --replication-factor 2
    Topic módosítása:
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --alter --topic test_topic --partitions 2 --replication-factor 2
    Topic törlése 
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --delete --topic test_topic
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --delete --topic test_stream_topic
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --delete --topic test_stream_output_topic
    Listázás
        docker exec -it  kafka_wz_node_1 kafka-topics --bootstrap-server kafka_wz_node_1:9092 --list
    Elindítani egy console-os consumert (és mindent olvasni az elejétől)
        docker exec -it  kafka_wz_node_1 kafka-console-consumer --bootstrap-server kafka_wz_node_1:9092 --topic test_topic --from-beginning --max-messages 100
        docker exec -it  kafka_wz_node_1 kafka-console-consumer --bootstrap-server kafka_wz_node_1:9092 --topic test_topic
    Törölni mindent    
        docker-compose rm -svf
    Újraindítani    
        docker-compose restart
