package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class KillYoukaiDamageTypes {
    public static final ResourceKey<DamageType> MAGIC_KNIVES = ResourceKey.create(Registries.DAMAGE_TYPE, KillYoukaiWithKnives.asResource("magic_knives"));
    public static final ResourceKey<DamageType> COOLDOWN_BYPASSING_MAGIC_KNIVES = ResourceKey.create(Registries.DAMAGE_TYPE, KillYoukaiWithKnives.asResource("cooldown_bypassing_magic_knives"));
}
