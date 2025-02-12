/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.gui.render.FontRenderer;
import com.wynntils.gui.render.RenderUtils;
import com.wynntils.gui.render.Texture;
import com.wynntils.gui.screens.CharacterSelectorScreen;
import com.wynntils.wynn.model.CharacterSelectionManager;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class PlayButton extends AbstractButton {
    private static final List<Component> TOOLTIP = List.of(
            new TranslatableComponent("screens.wynntils.characterSelection.playButton.play")
                    .withStyle(ChatFormatting.GREEN),
            new TranslatableComponent("screens.wynntils.characterSelection.playButton.description")
                    .withStyle(ChatFormatting.GRAY));
    private final CharacterSelectorScreen characterSelectorScreen;

    public PlayButton(int x, int y, int width, int height, CharacterSelectorScreen characterSelectorScreen) {
        super(x, y, width, height, new TextComponent("Play Button"));
        this.characterSelectorScreen = characterSelectorScreen;
    }

    @Override
    public void onPress() {
        if (characterSelectorScreen.getSelected() != null) {
            int slot = characterSelectorScreen.getSelected().getClassInfo().slot();
            CharacterSelectionManager.playWithCharacter(slot);
        }
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        RenderUtils.drawTexturedRect(
                poseStack,
                Texture.PLAY_BUTTON.resource(),
                this.x,
                this.y,
                0,
                this.width,
                this.height,
                0,
                characterSelectorScreen.getSelected() == null ? Texture.PLAY_BUTTON.height() / 2f : 0,
                Texture.PLAY_BUTTON.width(),
                Texture.PLAY_BUTTON.height() / 2f,
                Texture.PLAY_BUTTON.width(),
                Texture.PLAY_BUTTON.height());

        if (isHovered) {
            poseStack.pushPose();
            List<ClientTooltipComponent> clientTooltipComponents =
                    RenderUtils.componentToClientTooltipComponent(TOOLTIP);
            poseStack.translate(
                    mouseX
                            - RenderUtils.getToolTipWidth(
                                    clientTooltipComponents,
                                    FontRenderer.getInstance().getFont()),
                    mouseY - RenderUtils.getToolTipHeight(clientTooltipComponents),
                    100);
            RenderUtils.drawTooltip(
                    poseStack, TOOLTIP, FontRenderer.getInstance().getFont(), true);
            poseStack.popPose();
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {}
}
