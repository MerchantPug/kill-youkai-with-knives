package net.merchantpug.killyoukaiwithknives.platform;

import net.fabricmc.loader.api.FabricLoader;

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
}
