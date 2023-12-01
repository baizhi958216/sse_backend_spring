package cn.baizhi958216.sse_backend_spring.controller;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/sse")
@CrossOrigin()
public class SseController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter(10000L);

        // 添加新的 emitter 到列表
        emitters.add(emitter);

        // 在连接关闭时移除 emitter
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @GetMapping("/sendEvent")
    @Scheduled(fixedDelay = 1000)
    public void sendEvent(){
        this.sendMessage("我永远喜欢爱莉希雅 " + new Date());
    }

    // 通过调用该方法发送消息给所有连接的客户端
    public void sendMessage(String message) {
        for (SseEmitter emitter : emitters) {
            try {
                // 发送消息
                emitter.send(SseEmitter.event().reconnectTime(1000L).data(message).name("elysia"));
            } catch (IOException e) {
                // 发送失败时移除 emitter
                emitters.remove(emitter);
            }
        }
    }
}
