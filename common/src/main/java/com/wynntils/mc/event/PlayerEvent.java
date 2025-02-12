/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.event;

import net.minecraft.world.entity.player.Player;

public abstract class PlayerEvent extends LivingEvent {
    private final Player entityPlayer;

    protected PlayerEvent(Player player) {
        super(player);
        this.entityPlayer = player;
    }

    protected Player getPlayer() {
        return this.entityPlayer;
    }
}
