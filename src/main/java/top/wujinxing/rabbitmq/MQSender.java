package top.wujinxing.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.wujinxing.redis.RedisService;

/**
 * @author wujinxing
 * date 2019 2019/8/8 16:36
 * description
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    /*public void send(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send message:"+msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, message);
    }

    public void sendTopic(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send topic message: " + msg);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
    }

    //fanout
    public void sendFanout(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send fanout message: " + msg);
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, msg+"1");
    }

    //headers
    public void sendHeaders(Object message){
        String msg = RedisService.beanToString(message);
        log.info("send headers message: " + msg);
        MessageProperties properties = new MessageProperties();
        properties.setHeader("headers1", "value1");
        properties.setHeader("headers2", "value2");
        Message obj = new Message(msg.getBytes(), properties);
        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "",obj);
    }*/

    //将秒杀信息对象使用rabbitmq
    public void sendSeckillMessage(SeckillMessage message){
        String msg = RedisService.beanToString(message);
        log.info("send seckill message: " + msg);
        amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
    }
}
