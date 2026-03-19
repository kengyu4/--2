package com.hao.topic.ai.config;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ai相关配置
 * 支持 OpenAI 兼容接口（如阿里云百炼/Qwen）和 Anthropic Claude Sonnet 模型
 * 通过配置 spring.ai.provider=anthropic 切换至 Claude Sonnet
 */
@Configuration
public class ChatConfiguration {

    /**
     * 默认使用 OpenAI 兼容接口（阿里云百炼 Qwen 等）
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.ai.provider", havingValue = "openai", matchIfMissing = true)
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel)
                .build();
    }

    /**
     * Claude Sonnet 模型（通过 GitHub Copilot 教育福利或 Anthropic API Key 使用）
     * 在配置文件中设置 spring.ai.provider=anthropic 启用
     */
    @Bean
    @ConditionalOnProperty(name = "spring.ai.provider", havingValue = "anthropic")
    public ChatClient claudeChatClient(AnthropicChatModel anthropicChatModel) {
        return ChatClient.builder(anthropicChatModel)
                .build();
    }

}
