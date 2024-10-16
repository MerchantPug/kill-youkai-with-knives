package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class KillYoukaiItems {
    public static final Item POCKET_WATCH_OF_BLOOD = new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

    public static void registerAll(RegistrationCallback<Item> callback) {
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("pocket_watch_of_blood"), POCKET_WATCH_OF_BLOOD);
    }
}
