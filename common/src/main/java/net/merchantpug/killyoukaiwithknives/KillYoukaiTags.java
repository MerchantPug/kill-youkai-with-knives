package net.merchantpug.killyoukaiwithknives;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class KillYoukaiTags {

    public static class DamageTypes {
        public static final TagKey<DamageType> KNIVES_BYPASS_COOLDOWN_AFTER = TagKey.create(Registries.DAMAGE_TYPE, KillYoukaiWithKnives.asResource("knives_bypass_cooldown_after"));
    }

    public static class Enchantments {
        public static final TagKey<Enchantment> MAGIC_KNIVES_EXCLUSIVE = TagKey.create(Registries.ENCHANTMENT, KillYoukaiWithKnives.asResource("exclusive_set/magic_knives"));
    }

    public static class Items {
        public static final TagKey<Item> MAGIC_KNIVES_ENCHANTABLE = TagKey.create(Registries.ITEM, KillYoukaiWithKnives.asResource("enchantable/magic_knives"));
    }
}
