package de.safespacegerman.core.utils;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * SpaceCore; de.safespacegerman.core.utils:DiscordChatRelay
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 29.03.2023
 */
public class DiscordWebhook {
    private static Map<String, WebhookClient> webhookClients = new HashMap<>();

    private DiscordWebhook() {} // prevent instantiation

    public static void executeHook(WebhookMessageBuilder message, String url) {
        try {
            if (!webhookClients.containsKey(url)) {
                WebhookClientBuilder builder = new WebhookClientBuilder(url);
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job, "webhookClient-" + url);
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                webhookClients.put(url, builder.build());
            }
            webhookClients.get(url).send(message.build());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}