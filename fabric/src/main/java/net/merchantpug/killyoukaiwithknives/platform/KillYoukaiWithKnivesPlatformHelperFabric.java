package net.merchantpug.killyoukaiwithknives.platform;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.merchantpug.killyoukaiwithknives.network.clientbound.SyncTimestasisStateClientboundPacket;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KillYoukaiWithKnivesPlatformHelperFabric implements KillYoukaiWithKnivesPlatformHelper {

    @Override
    public Platform getPlatform() {
        return Platform.FABRIC;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void sendTrackingClientboundPacket(Entity entity, CustomPacketPayload payload) {
        for (ServerPlayer other : PlayerLookup.tracking(entity))
            ServerPlayNetworking.send(other, payload);
        if (entity instanceof ServerPlayer player)
            ServerPlayNetworking.send(player, payload);
    }

    @Override
    public boolean previouslyHurtByKnives(Entity entity, Entity directAttacker) {
        return entity.hasAttached(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) && entity.getAttached(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) == directAttacker.getUUID();
    }

    @Override
    public boolean isTimestasised(Entity entity) {
        return entity.hasAttached(KillYoukaiAttachments.IS_TIMESTASISED) && entity.getAttached(KillYoukaiAttachments.IS_TIMESTASISED);
    }

    @Override
    public void setTimestasised(Entity entity, boolean value, @Nullable Vec3 pos) {
        if (!value) {
            entity.removeAttached(KillYoukaiAttachments.IS_TIMESTASISED);
            entity.removeAttached(KillYoukaiAttachments.TIMESTASIS_POSITION);
        }
        entity.setAttached(KillYoukaiAttachments.IS_TIMESTASISED, value);
        if (pos != null)
            entity.setAttached(KillYoukaiAttachments.TIMESTASIS_POSITION, pos);
        if (!entity.level().isClientSide())
            sendTrackingClientboundPacket(entity, new SyncTimestasisStateClientboundPacket(entity.getId(), value, Optional.ofNullable(pos)));
    }

    @Override
    @Nullable
    public Vec3 getTimestasisPos(Entity entity) {
        return entity.getAttached(KillYoukaiAttachments.TIMESTASIS_POSITION);
    }
}
