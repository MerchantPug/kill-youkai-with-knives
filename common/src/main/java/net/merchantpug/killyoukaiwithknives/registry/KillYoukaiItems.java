package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.item.MagicKnivesItem;
import net.merchantpug.killyoukaiwithknives.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class KillYoukaiItems {
    public static final Item MAGIC_KNIVES = new MagicKnivesItem(new Item.Properties().durability(256).rarity(Rarity.EPIC));

    public static void registerAll(RegistrationCallback<Item> callback) {
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("magic_knives"), MAGIC_KNIVES);
    }
}
