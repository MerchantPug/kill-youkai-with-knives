package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.registry.HolderRegistrationCallback;
import net.merchantpug.killyoukaiwithknives.sound.KillYoukaiSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;

public class KillYoukaiArmorMaterials {
    public static Holder<ArmorMaterial> MAID;

    public static void registerAll(HolderRegistrationCallback<ArmorMaterial> callback) {
        MAID = callback.register(BuiltInRegistries.ARMOR_MATERIAL, KillYoukaiWithKnives.asResource("maid"), new ArmorMaterial(Map.of(), 0, KillYoukaiSoundEvents.ARMOR_EQUIP_MAID, () -> Ingredient.of(Items.PHANTOM_MEMBRANE), List.of(new ArmorMaterial.Layer(KillYoukaiWithKnives.asResource("maid"))), 0.0F, 0.0F));
    }
}
