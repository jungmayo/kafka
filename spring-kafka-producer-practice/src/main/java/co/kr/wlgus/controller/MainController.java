package co.kr.wlgus.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/publish/topic1")
    public String publish1(){

        for(int i=1; i<=10000; i++){
            // 토픽발행
            kafkaTemplate.send("my-topic-01", "publish1-message-"+i);
        }

        return "done";
    }
    @GetMapping("/publish/topic2")
    public String publish2(){
        for(int i=1; i<=100000; i++){
            // 토픽발행
            kafkaTemplate.send("my-topic-02", "publish2-message-"+i);
        }
        return "done";
    }
    @GetMapping("/publish/topic3")
    public String publish3(){

        ExecutorService exeService = Executors.newFixedThreadPool(10);
        for(int i=1; i<=10000000; i++){

            final int finalID = i;

            //스레드 실행
            exeService.submit(()->{
                //토픽발행
                kafkaTemplate.send("my-topic-03", "publish-message-"+finalID);
            });
        }
        //스레드 종료
        exeService.shutdown();
        return "done";
    }

}
