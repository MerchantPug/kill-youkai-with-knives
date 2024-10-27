package net.merchantpug.killyoukaiwithknives.enchantment;

import net.merchantpug.killyoukaiwithknives.KillYoukaiTags;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.enchantment.effect.SummonTimestasisEffect;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;

public class KillYoukaiEnchantments {
    public static final ResourceKey<Enchantment> TIMESTASIS = ResourceKey.create(Registries.ENCHANTMENT, KillYoukaiWithKnives.asResource("timestasis"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        HolderSet<Item> magicKnivesEnchantable = items.getOrThrow(KillYoukaiTags.Items.MAGIC_KNIVES_ENCHANTABLE);

        Enchantment timestasis = Enchantment.enchantment(
                Enchantment.definition(magicKnivesEnchantable,
                        5,
                        2,
                        Enchantment.dynamicCost(12, 7),
                        Enchantment.constantCost(50),
                        2,
                        EquipmentSlotGroup.HAND)
                )
                .withEffect(KillYoukaiEnchantmentEffectComponents.SUMMON_TIMESTASIS, new SummonTimestasisEffect(LevelBasedValue.constant(0.25F), LevelBasedValue.constant(12.0F), LevelBasedValue.perLevel(120, 60)))
                .build(TIMESTASIS.location());
        context.register(TIMESTASIS, timestasis);
    }
}
