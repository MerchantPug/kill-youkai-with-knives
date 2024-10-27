package net.merchantpug.killyoukaiwithknives.damage;

import net.merchantpug.killyoukaiwithknives.KillYoukaiTags;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantmentEffectComponents;
import net.merchantpug.killyoukaiwithknives.enchantment.effect.SummonTimestasisEffect;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public class KillYoukaiDamageTypes {
    public static final ResourceKey<DamageType> MAGIC_KNIVES = ResourceKey.create(Registries.DAMAGE_TYPE, KillYoukaiWithKnives.asResource("magic_knives"));
    public static final ResourceKey<DamageType> COOLDOWN_BYPASSING_MAGIC_KNIVES = ResourceKey.create(Registries.DAMAGE_TYPE, KillYoukaiWithKnives.asResource("cooldown_bypassing_magic_knives"));


    public static void bootstrap(BootstrapContext<DamageType> context) {
        DamageType magicKnives = new DamageType("magicKnives", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F);
        context.register(MAGIC_KNIVES, magicKnives);
        context.register(COOLDOWN_BYPASSING_MAGIC_KNIVES, magicKnives);
    }
}
