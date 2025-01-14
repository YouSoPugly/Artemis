/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.gui.screens.maps;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wynntils.core.webapi.TerritoryManager;
import com.wynntils.features.user.map.MapFeature;
import com.wynntils.gui.render.RenderUtils;
import com.wynntils.mc.objects.Location;
import com.wynntils.mc.utils.McUtils;
import com.wynntils.sockets.model.HadesUserModel;
import com.wynntils.sockets.objects.HadesUser;
import com.wynntils.utils.BoundingBox;
import com.wynntils.utils.KeyboardUtils;
import com.wynntils.wynn.model.CompassModel;
import com.wynntils.wynn.model.map.MapModel;
import com.wynntils.wynn.model.map.poi.PlayerPoi;
import com.wynntils.wynn.model.map.poi.Poi;
import com.wynntils.wynn.model.map.poi.TerritoryPoi;
import com.wynntils.wynn.model.map.poi.WaypointPoi;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;

public class MainMapScreen extends AbstractMapScreen {
    public MainMapScreen() {
        super();
        centerMapAroundPlayer();
    }

    public MainMapScreen(float mapCenterX, float mapCenterZ) {
        super(mapCenterX, mapCenterZ);
        updateMapCenter(mapCenterX, mapCenterZ);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (holdingMapKey && !MapFeature.INSTANCE.openMapKeybind.getKeyMapping().isDown()) {
            this.onClose();
            return;
        }

        updateMapCenterIfDragging(mouseX, mouseY);

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        RenderSystem.enableDepthTest();

        renderMap(poseStack, MapFeature.INSTANCE.renderUsingLinear);

        RenderUtils.enableScissor(
                (int) (renderX + renderedBorderXOffset), (int) (renderY + renderedBorderYOffset), (int) mapWidth, (int)
                        mapHeight);

        renderPois(poseStack, mouseX, mouseY);

        // Cursor
        renderCursor(
                poseStack,
                MapFeature.INSTANCE.playerPointerScale,
                MapFeature.INSTANCE.pointerColor,
                MapFeature.INSTANCE.pointerType);

        RenderSystem.disableScissor();

        renderBackground(poseStack);

        renderCoordinates(poseStack, mouseX, mouseY);
    }

    private void renderPois(PoseStack poseStack, int mouseX, int mouseY) {
        List<Poi> pois = new ArrayList<>();

        pois.addAll(MapModel.getServicePois());
        pois.addAll(MapModel.getLabelPois());

        List<HadesUser> renderedPlayers = HadesUserModel.getHadesUserMap().values().stream()
                .filter(
                        hadesUser -> (hadesUser.isPartyMember() && MapFeature.INSTANCE.renderRemotePartyPlayers)
                                || (hadesUser.isMutualFriend() && MapFeature.INSTANCE.renderRemoteFriendPlayers)
                        /*|| (hadesUser.isGuildMember() && MapFeature.INSTANCE.renderRemoteGuildPlayers)*/ )
                .sorted(Comparator.comparing(
                        hadesUser -> hadesUser.getMapLocation().getY()))
                .toList();

        pois.sort(Comparator.comparing(poi -> poi.getLocation().getY()));

        // Make sure compass and player pois are on top
        pois.addAll(renderedPlayers.stream().map(PlayerPoi::new).toList());
        CompassModel.getCompassWaypoint().ifPresent(pois::add);
        if (KeyboardUtils.isControlDown()) {
            pois.addAll(TerritoryManager.getTerritoryPois());
        }

        renderPois(
                pois,
                poseStack,
                BoundingBox.centered(mapCenterX, mapCenterZ, width / currentZoom, height / currentZoom),
                MapFeature.INSTANCE.poiScale,
                mouseX,
                mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            if (McUtils.mc().player.isShiftKeyDown()
                    && CompassModel.getCompassLocation().isPresent()) {
                Location location = CompassModel.getCompassLocation().get();
                updateMapCenter((float) location.x, (float) location.z);
                return true;
            }

            centerMapAroundPlayer();
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            if (hovered instanceof WaypointPoi) {
                CompassModel.reset();
                return true;
            }

            if (hovered != null && !(hovered instanceof TerritoryPoi)) {
                McUtils.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                if (hovered.hasStaticLocation()) {
                    CompassModel.setCompassLocation(new Location(hovered.getLocation()));
                } else {
                    final Poi finalHovered = hovered;
                    CompassModel.setDynamicCompassLocation(
                            () -> finalHovered.getLocation().asLocation());
                }
                return true;
            }

            super.mouseClicked(mouseX, mouseY, button);
        } else if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            setCompassToMouseCoords(mouseX, mouseY);
        }

        return true;
    }

    private void setCompassToMouseCoords(double mouseX, double mouseY) {
        double gameX = (mouseX - centerX) / currentZoom + mapCenterX;
        double gameZ = (mouseY - centerZ) / currentZoom + mapCenterZ;
        Location compassLocation = new Location(gameX, 0, gameZ);
        CompassModel.setCompassLocation(compassLocation);

        McUtils.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
    }

    public void setHovered(Poi hovered) {
        this.hovered = hovered;
    }
}
