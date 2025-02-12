/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.features.user;

import com.wynntils.core.config.Config;
import com.wynntils.core.config.ConfigHolder;
import com.wynntils.core.features.UserFeature;
import com.wynntils.core.managers.Model;
import com.wynntils.hades.protocol.enums.SocialType;
import com.wynntils.sockets.model.HadesModel;
import com.wynntils.sockets.model.HadesUserModel;
import com.wynntils.wynn.model.ActionBarModel;
import com.wynntils.wynn.model.PlayerRelationsModel;
import java.util.List;

public class HadesFeature extends UserFeature {
    public static HadesFeature INSTANCE;

    @Config
    public boolean getOtherPlayerInfo = true;

    @Config
    public boolean shareWithParty = true;

    @Config
    public boolean shareWithFriends = true;

    @Config
    public boolean shareWithGuild = true;

    @Override
    public List<Class<? extends Model>> getModelDependencies() {
        // SocketModel
        //      needs ActionBarModel for updating player info
        //      HadesUserModel for storing remote HadesUser info
        //      PlayerRelationsModel to parse player relations
        return List.of(HadesModel.class, PlayerRelationsModel.class, HadesUserModel.class, ActionBarModel.class);
    }

    @Override
    protected void onConfigUpdate(ConfigHolder configHolder) {
        switch (configHolder.getFieldName()) {
            case "getOtherPlayerInfo" -> {
                if (getOtherPlayerInfo) {
                    HadesModel.tryResendWorldData();
                } else {
                    HadesUserModel.getHadesUserMap().clear();
                }
            }
            case "shareWithParty" -> {
                if (shareWithParty) {
                    PlayerRelationsModel.requestPartyListUpdate();
                } else {
                    HadesModel.resetSocialType(SocialType.PARTY);
                }
            }
            case "shareWithFriends" -> {
                if (shareWithFriends) {
                    PlayerRelationsModel.requestFriendListUpdate();
                } else {
                    HadesModel.resetSocialType(SocialType.FRIEND);
                }
            }
            case "shareWithGuild" -> {
                // TODO
            }
        }
    }
}
