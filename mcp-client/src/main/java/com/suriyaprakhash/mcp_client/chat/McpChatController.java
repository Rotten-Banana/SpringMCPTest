package com.suriyaprakhash.mcp_client.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Objects;


@RequestMapping("mcp-chat")
@RestController
public class McpChatController {

    private final ChatClient chatClient;

    private final SyncMcpToolCallbackProvider syncMcpToolCallbackProvider;

    public McpChatController(ChatClient.Builder chatClientBuilder,
                             @Qualifier("my-mcp-server-callback-tool-provider") SyncMcpToolCallbackProvider syncMcpToolCallbackProvider) {
        this.chatClient = chatClientBuilder.build();
        this.syncMcpToolCallbackProvider = syncMcpToolCallbackProvider;
    }

    @GetMapping("{prompt}")
    public Mono<String> chat(@PathVariable String prompt) {
        return Mono.just(Objects.requireNonNull(chatClient.prompt()
                .user(prompt)
                .toolCallbacks(syncMcpToolCallbackProvider)
                .call().content()));
    }
}
