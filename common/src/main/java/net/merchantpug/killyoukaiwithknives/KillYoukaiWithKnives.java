package net.merchantpug.killyoukaiwithknives;

import net.merchantpug.killyoukaiwithknives.platform.KillYoukaiWithKnivesPlatformHelper;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillYoukaiWithKnives {
    public static final String MOD_ID = "killyoukaiwithknives";
    public static final String MOD_NAME = "Kill Youkai With Knives";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    private static KillYoukaiWithKnivesPlatformHelper helper;

    public static void init() {

    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static KillYoukaiWithKnivesPlatformHelper getHelper() {
        return helper;
    }

    public static void setHelper(KillYoukaiWithKnivesPlatformHelper helper) {
        KillYoukaiWithKnives.helper = helper;
    }
}