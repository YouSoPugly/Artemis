/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.wynn.item.properties;

import com.wynntils.gui.render.FontRenderer;
import com.wynntils.mc.objects.CommonColors;
import com.wynntils.mc.utils.ComponentUtils;
import com.wynntils.mc.utils.ItemUtils;
import com.wynntils.wynn.item.WynnItemStack;

public class DailyRewardMultiplierProperty extends CustomStackCountProperty {
    public DailyRewardMultiplierProperty(WynnItemStack item) {
        super(item);
        try {
            // Multiplier line is always on index 3
            String loreLine =
                    ComponentUtils.stripFormatting(ItemUtils.getLore(item).get(3));
            String value = String.valueOf(loreLine.charAt(loreLine.indexOf("Streak Multiplier: ") + 19));
            this.setCustomStackCount(value, CommonColors.WHITE, FontRenderer.TextShadow.NORMAL);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
