/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.gui.render.FontRenderer;
import com.wynntils.gui.render.HorizontalAlignment;
import com.wynntils.gui.render.Texture;
import com.wynntils.mc.objects.CommonColors;
import net.minecraft.network.chat.Component;

public abstract class WynntilsMenuPagedScreenBase extends WynntilsMenuScreenBase {
    protected WynntilsMenuPagedScreenBase(Component component) {
        super(component);
    }

    public abstract int getCurrentPage();

    public abstract void setCurrentPage(int currentPage);

    public abstract int getMaxPage();

    protected void renderPageInfo(PoseStack poseStack, int currentPage, int maxPage) {
        FontRenderer.getInstance()
                .renderAlignedTextInBox(
                        poseStack,
                        (currentPage) + " / " + (maxPage),
                        Texture.QUEST_BOOK_BACKGROUND.width() / 2f,
                        Texture.QUEST_BOOK_BACKGROUND.width(),
                        Texture.QUEST_BOOK_BACKGROUND.height() - 25,
                        0,
                        CommonColors.BLACK,
                        HorizontalAlignment.Center,
                        FontRenderer.TextShadow.NONE);
    }
}
