package net.merchantpug.killyoukaiwithknives.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.minecraft.world.entity.Entity;

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
    public boolean previouslyHurtByKnives(Entity entity, Entity directAttacker) {
        return entity.hasAttached(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) && entity.getAttached(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) == directAttacker.getUUID();
    }
}
