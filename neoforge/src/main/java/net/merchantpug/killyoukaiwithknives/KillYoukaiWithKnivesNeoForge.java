package net.merchantpug.killyoukaiwithknives;


import net.merchantpug.killyoukaiwithknives.platform.KillYoukaiWithKnivesPlatformHelperNeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(KillYoukaiWithKnives.MOD_ID)
public class KillYoukaiWithKnivesNeoForge {

    public KillYoukaiWithKnivesNeoForge(IEventBus eventBus) {
        KillYoukaiWithKnives.init();
        KillYoukaiWithKnives.setHelper(new KillYoukaiWithKnivesPlatformHelperNeoForge());
    }
}