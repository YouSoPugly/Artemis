/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class PlayerArmorRenderEvent extends Event {
    private final Player player;
    private final EquipmentSlot slot;

    public PlayerArmorRenderEvent(Player player, EquipmentSlot slot) {
        this.player = player;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }
}
