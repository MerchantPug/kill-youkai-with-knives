package net.merchantpug.killyoukaiwithknives.platform;

import net.merchantpug.killyoukaiwithknives.network.clientbound.SyncTimestasisStateClientboundPacket;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class KillYoukaiWithKnivesPlatformHelperNeoForge implements KillYoukaiWithKnivesPlatformHelper {

    @Override
    public Platform getPlatform() {
        return Platform.NEOFORGE;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public void sendTrackingClientboundPacket(Entity entity, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, payload);
    }

    @Override
    public boolean previouslyHurtByKnives(Entity entity, Entity directAttacker) {
        return entity.hasData(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) && entity.getData(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) == directAttacker.getUUID();
    }


    @Override
    public boolean isTimestasised(Entity entity) {
        return entity.hasData(KillYoukaiAttachments.IS_TIMESTASISED) && entity.getData(KillYoukaiAttachments.IS_TIMESTASISED);
    }

    @Override
    public void setTimestasised(Entity entity, boolean value, @Nullable Vec3 pos) {
        if (!value) {
            entity.removeData(KillYoukaiAttachments.IS_TIMESTASISED);
            entity.removeData(KillYoukaiAttachments.TIMESTASIS_POSITION);
        }
        entity.setData(KillYoukaiAttachments.IS_TIMESTASISED, value);
        if (pos != null)
            entity.setData(KillYoukaiAttachments.TIMESTASIS_POSITION, pos);
        if (!entity.level().isClientSide())
            sendTrackingClientboundPacket(entity, new SyncTimestasisStateClientboundPacket(entity.getId(), value, Optional.ofNullable(pos)));
    }

    @Override
    public @Nullable Vec3 getTimestasisPos(Entity entity) {
        return entity.getExistingData(KillYoukaiAttachments.TIMESTASIS_POSITION).orElse(null);
    }
}