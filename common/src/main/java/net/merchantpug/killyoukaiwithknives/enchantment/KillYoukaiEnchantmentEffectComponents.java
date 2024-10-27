package net.merchantpug.killyoukaiwithknives.enchantment;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.enchantment.effect.SummonTimestasisEffect;
import net.merchantpug.killyoukaiwithknives.registry.RegistrationCallback;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class KillYoukaiEnchantmentEffectComponents {
    public static final DataComponentType<List<ConditionalEffect<Unit>>> AUTOMATIC_SCAVENGE = DataComponentType.<List<ConditionalEffect<Unit>>>builder()
            .persistent(ConditionalEffect.codec(Unit.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf())
            .build();
    public static final DataComponentType<List<ConditionalEffect<Unit>>> SCAVENGE_PROJECTILES = DataComponentType.<List<ConditionalEffect<Unit>>>builder()
            .persistent(ConditionalEffect.codec(Unit.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf())
            .build();
    public static final DataComponentType<List<ConditionalEffect<SummonTimestasisEffect>>> SUMMON_TIMESTASIS = DataComponentType.<List<ConditionalEffect<SummonTimestasisEffect>>>builder()
            .persistent(ConditionalEffect.codec(SummonTimestasisEffect.CODEC, LootContextParamSets.ENCHANTED_DAMAGE).listOf())
            .build();

    public static void registerAll(RegistrationCallback<DataComponentType<?>> callback) {
        callback.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, KillYoukaiWithKnives.asResource("automatic_scavenge"), AUTOMATIC_SCAVENGE);
        callback.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, KillYoukaiWithKnives.asResource("scavenge_projectiles"), SCAVENGE_PROJECTILES);
        callback.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, KillYoukaiWithKnives.asResource("summon_timestasis"), SUMMON_TIMESTASIS);
    }
}
