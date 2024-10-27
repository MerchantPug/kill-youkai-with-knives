package net.merchantpug.killyoukaiwithknives;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class KillYoukaiTags {
    public static class DamageTypes {
        public static final TagKey<DamageType> KNIVES_BYPASS_COOLDOWN_AFTER = TagKey.create(Registries.DAMAGE_TYPE, KillYoukaiWithKnives.asResource("knives_bypass_cooldown_after"));
    }
}
