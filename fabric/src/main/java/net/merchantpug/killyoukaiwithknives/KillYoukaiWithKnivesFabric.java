package net.merchantpug.killyoukaiwithknives;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.merchantpug.killyoukaiwithknives.attribute.KillYoukaiAttributes;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantmentEffectComponents;
import net.merchantpug.killyoukaiwithknives.entity.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiArmorMaterials;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.network.clientbound.SyncTimestasisStateClientboundPacket;
import net.merchantpug.killyoukaiwithknives.sound.KillYoukaiSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class KillYoukaiWithKnivesFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        KillYoukaiWithKnives.init();
        registerContent();
        registerPackets();
        modifyCreativeTabs();
    }

    private static void registerContent() {
        KillYoukaiAttributes.registerAll();
        KillYoukaiSoundEvents.registerAll();
        KillYoukaiArmorMaterials.registerAll(Registry::registerForHolder);
        KillYoukaiEnchantmentEffectComponents.registerAll(Registry::register);
        KillYoukaiEntityTypes.registerAll(Registry::register);
        KillYoukaiItems.registerAll(Registry::register);
    }

    private static void registerPackets() {
        PayloadTypeRegistry.playS2C().register(SyncTimestasisStateClientboundPacket.TYPE, SyncTimestasisStateClientboundPacket.STREAM_CODEC);
    }

    private static void modifyCreativeTabs() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.addAfter(Items.MACE, KillYoukaiItems.MAGIC_KNIVES);
            entries.addAfter(Items.TURTLE_HELMET, KillYoukaiItems.MAID_BONNET, KillYoukaiItems.MAID_DRESS, KillYoukaiItems.MAID_LEGGINGS, KillYoukaiItems.MAID_BOOTS);
        });
    }
}
