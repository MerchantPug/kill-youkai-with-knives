package net.merchantpug.killyoukaiwithknives;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantmentEffectComponents;
import net.merchantpug.killyoukaiwithknives.entity.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.network.clientbound.SyncTimestasisStateClientboundPacket;
import net.minecraft.core.Registry;

public class KillYoukaiWithKnivesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        KillYoukaiWithKnives.init();
        registerContent();
        registerPackets();
    }

    private static void registerContent() {
        KillYoukaiEnchantmentEffectComponents.registerAll(Registry::register);
        KillYoukaiEntityTypes.registerAll(Registry::register);
        KillYoukaiItems.registerAll(Registry::register);
    }

    private static void registerPackets() {
        PayloadTypeRegistry.playS2C().register(SyncTimestasisStateClientboundPacket.TYPE, SyncTimestasisStateClientboundPacket.STREAM_CODEC);
    }
}
