package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.registry.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class KillYoukaiItems {
    public static final Item MAGIC_KNIVES = new MagicKnivesItem(new Item.Properties().attributes(MagicKnivesItem.createAttributes()).durability(128).rarity(Rarity.EPIC));

    public static void registerAll(RegistrationCallback<Item> callback) {
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("magic_knives"), MAGIC_KNIVES);
    }
}
