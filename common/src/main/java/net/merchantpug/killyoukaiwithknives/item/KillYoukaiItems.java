package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.registry.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

public class KillYoukaiItems {
    public static final Item MAGIC_KNIVES = new MagicKnivesItem(new Item.Properties().attributes(MagicKnivesItem.createAttributes()).durability(160).rarity(Rarity.EPIC));

    public static final ArmorItem MAID_BONNET = new MaidArmorItem(KillYoukaiArmorMaterials.MAID, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).attributes(new ItemAttributeModifiers(List.of(), false)));
    public static final ArmorItem MAID_DRESS = new MaidArmorItem(KillYoukaiArmorMaterials.MAID, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).attributes(new ItemAttributeModifiers(List.of(), false)));
    public static final ArmorItem MAID_LEGGINGS = new MaidArmorItem(KillYoukaiArmorMaterials.MAID, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).attributes(new ItemAttributeModifiers(List.of(), false)));
    public static final ArmorItem MAID_BOOTS = new MaidArmorItem(KillYoukaiArmorMaterials.MAID, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).attributes(new ItemAttributeModifiers(List.of(), false)));

    public static void registerAll(RegistrationCallback<Item> callback) {
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("magic_knives"), MAGIC_KNIVES);
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("maid_bonnet"), MAID_BONNET);
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("maid_dress"), MAID_DRESS);
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("maid_leggings"), MAID_LEGGINGS);
        callback.register(BuiltInRegistries.ITEM, KillYoukaiWithKnives.asResource("maid_boots"), MAID_BOOTS);
    }
}
