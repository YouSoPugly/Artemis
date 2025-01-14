/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.event;

import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraftforge.eventbus.api.Event;

public class CommandsPacketEvent extends Event {
    private RootCommandNode<SharedSuggestionProvider> root;

    public CommandsPacketEvent(RootCommandNode<SharedSuggestionProvider> root) {
        this.root = root;
    }

    public RootCommandNode<SharedSuggestionProvider> getRoot() {
        return root;
    }

    public void setRoot(RootCommandNode<SharedSuggestionProvider> root) {
        this.root = root;
    }
}
