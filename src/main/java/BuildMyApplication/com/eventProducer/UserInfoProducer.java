package BuildMyApplication.com.eventProducer;


import BuildMyApplication.com.models.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {

    private final KafkaTemplate<String, UserInfoDto> kafkaTemplate;


    @Value("${spring.kafka.topic-json.name}")
    private String TOPIC_NAME;
    @Autowired
    UserInfoProducer(KafkaTemplate<String,UserInfoDto> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }



    public void sendEventToKafka(UserInfoDto userInfoDto){
        Message<UserInfoDto> message = MessageBuilder.withPayload(userInfoDto)
                .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME).build();

        try {
            System.out.println("error in producer:sending ");
            kafkaTemplate.send(message);
        }catch (Exception ex){
            System.out.println("error in producer: " +ex.getMessage());
        }

    }


}
