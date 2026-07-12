package com.github.cidarosa.algaposts.post.service.infrastructure.rabbitmq;

import com.github.cidarosa.algaposts.post.service.api.model.PostProcessingResult;
import com.github.cidarosa.algaposts.post.service.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitListenerConfig {

    private final PostService postService;

    @RabbitListener(queues = RabbitConfig.RESULT_QUEUE)
    public void consumeProcessing(@Payload PostProcessingResult result) {
        postService.updatePostInfo(result);
    }
}
