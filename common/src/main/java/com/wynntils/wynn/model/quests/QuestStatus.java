/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.wynn.model.quests;

import com.wynntils.gui.screens.WynntilsQuestBookScreen;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public enum QuestStatus {
    STARTED(new TextComponent("Started...").withStyle(ChatFormatting.YELLOW)),
    CAN_START(new TextComponent("Can start...").withStyle(ChatFormatting.YELLOW)),
    CANNOT_START(new TextComponent("Cannot start...").withStyle(ChatFormatting.RED)),
    COMPLETED(new TextComponent("Completed!").withStyle(ChatFormatting.GREEN));

    /** This component is used to reconstruct quest tooltip in {@link WynntilsQuestBookScreen}.
     */
    private final Component questBookComponent;

    QuestStatus(Component component) {
        this.questBookComponent = component;
    }

    public Component getQuestBookComponent() {
        return questBookComponent;
    }

    public static QuestStatus fromString(String str) {
        try {
            return QuestStatus.valueOf(str.toUpperCase(Locale.ROOT).replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            // Use CANNOT_START as fallback... it's as good as any
            return CANNOT_START;
        }
    }
}
