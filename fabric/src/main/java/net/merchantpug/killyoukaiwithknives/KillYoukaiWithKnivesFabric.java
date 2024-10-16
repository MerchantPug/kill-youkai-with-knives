package net.merchantpug.killyoukaiwithknives;

import net.fabricmc.api.ModInitializer;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiItems;
import net.minecraft.core.Registry;

public class KillYoukaiWithKnivesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        KillYoukaiWithKnives.init();
        registerContent();
    }

    private static void registerContent() {
        KillYoukaiItems.registerAll(Registry::register);
        KillYoukaiEntityTypes.registerAll(Registry::register);
    }
}
