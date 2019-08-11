package top.wujinxing.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author wujinxing
 * date 2019 2019/8/8 16:36
 * description rabbitmq配置
 */
@Configuration
public class MQConfig {

    public static final String SECKILL_QUEUE = "seckill.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queuq1";
    public static final String TOPIC_QUEUE2 = "topic.queuq2";
    public static final String TOPIC_EXCHANGE = "topicExchange";
    public static final String FANOUT_EXCHANGE = "fanoutExchange";
    public static final String HEADERS_EXCHANGE = "headersExchange";


    @Bean
    public Queue seckillQueue(){
        return new Queue(SECKILL_QUEUE, true);
    }

    /**
     * Direct模式 交换机Exchange
     * 发送者先发送到交换机上，然后交换机作为路由再将信息发到队列，
     */
    @Bean
    public Queue queue(){
        return new Queue(QUEUE, true);
    }

    /**
     * Topic模式 交换机Exchange
     */
    /*@Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE1, true);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE2, true);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }
    //绑定
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");//代表都能监听得到
    }

    *//**
     * Fanout模式 交换机Exchange
     *//*
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    *//**
     * Headers模式
     *//*
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    public static final String HEADERS_QUEUE = "headers_queue";

    @Bean
    public Queue headersQueue(){
        return new Queue(HEADERS_QUEUE, true);
    }
    @Bean
    public Binding headersBinding(){
        Map<String, Object> map = new HashMap<>();
        map.put("headers1", "value1");
        map.put("headers2", "value2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }
*/
}
