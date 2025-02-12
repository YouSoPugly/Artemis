/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.features.user;

import com.wynntils.core.commands.ClientCommandManager;
import com.wynntils.core.features.UserFeature;
import com.wynntils.mc.event.ChatSentEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommandsFeature extends UserFeature {
    @SubscribeEvent
    public void onChatSend(ChatSentEvent e) {
        String message = e.getMessage();

        if (message.startsWith("/")) {
            if (ClientCommandManager.handleCommand(message)) {
                e.setCanceled(true);
            }
        }
    }
}
