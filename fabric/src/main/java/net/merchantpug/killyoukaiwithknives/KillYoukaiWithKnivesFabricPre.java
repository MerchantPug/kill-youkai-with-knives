package net.merchantpug.killyoukaiwithknives;

import net.merchantpug.killyoukaiwithknives.platform.KillYoukaiWithKnivesPlatformHelperFabric;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public class KillYoukaiWithKnivesFabricPre implements PreLaunchEntrypoint {
    @Override
    public void onPreLaunch() {
        KillYoukaiWithKnives.setHelper(new KillYoukaiWithKnivesPlatformHelperFabric());
    }
}
