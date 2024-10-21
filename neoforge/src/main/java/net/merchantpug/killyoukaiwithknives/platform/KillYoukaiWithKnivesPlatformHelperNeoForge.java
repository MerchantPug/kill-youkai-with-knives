package net.merchantpug.killyoukaiwithknives.platform;

import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.minecraft.world.entity.Entity;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

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
    public boolean previouslyHurtByKnives(Entity entity, Entity directAttacker) {
        return entity.hasData(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) && entity.getData(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER) == directAttacker.getUUID();
    }
}