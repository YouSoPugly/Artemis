/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.mc.mixin;

import com.wynntils.core.managers.CrashReportManager;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {
    @Inject(
            method = "getDetails(Ljava/lang/StringBuilder;)V",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/SystemReport;appendToCrashReportString(Ljava/lang/StringBuilder;)V"))
    private void addWynntilsDetails(StringBuilder builder, CallbackInfo ci) {
        CrashReportCategory wynntilsCrashDetails = CrashReportManager.generateDetails();

        wynntilsCrashDetails.getDetails(builder);
        builder.append("\n\n");
    }
}
