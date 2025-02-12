/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.gui.widgets;

import com.wynntils.gui.render.Texture;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public record WynntilsMenuButton(
        Texture buttonTexture, boolean dynamicTexture, Screen openedScreen, List<Component> tooltipList) {}
