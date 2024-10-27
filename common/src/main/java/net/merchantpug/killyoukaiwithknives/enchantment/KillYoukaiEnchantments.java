package net.merchantpug.killyoukaiwithknives.enchantment;

import net.merchantpug.killyoukaiwithknives.KillYoukaiTags;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.enchantment.effect.SummonTimestasisEffect;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;

public class KillYoukaiEnchantments {
    public static final ResourceKey<Enchantment> SCATTER = ResourceKey.create(Registries.ENCHANTMENT, KillYoukaiWithKnives.asResource("scatter"));
    public static final ResourceKey<Enchantment> SCAVENGE = ResourceKey.create(Registries.ENCHANTMENT, KillYoukaiWithKnives.asResource("scavenge"));
    public static final ResourceKey<Enchantment> TIMECOLLECTION = ResourceKey.create(Registries.ENCHANTMENT, KillYoukaiWithKnives.asResource("timecollection"));
    public static final ResourceKey<Enchantment> TIMESTASIS = ResourceKey.create(Registries.ENCHANTMENT, KillYoukaiWithKnives.asResource("timestasis"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);

        HolderSet<Item> magicKnivesEnchantable = items.getOrThrow(KillYoukaiTags.Items.MAGIC_KNIVES_ENCHANTABLE);
        HolderSet<Enchantment> magicKnivesExclusive = enchantments.getOrThrow(KillYoukaiTags.Enchantments.MAGIC_KNIVES_EXCLUSIVE);

        Enchantment scatter = Enchantment.enchantment(
                Enchantment.definition(magicKnivesEnchantable,
                        3,
                        3,
                        Enchantment.dynamicCost(2, 7),
                        Enchantment.constantCost(35),
                        2,
                        EquipmentSlotGroup.HAND)
                )
                .withEffect(EnchantmentEffectComponents.PROJECTILE_COUNT, new AddValue(LevelBasedValue.perLevel(2.0F, 1.0F)))
                .withEffect(EnchantmentEffectComponents.PROJECTILE_SPREAD, new AddValue(LevelBasedValue.perLevel(6.0F, 3.0F)))
                .build(SCATTER.location());
        Enchantment scavenge = Enchantment.enchantment(
                        Enchantment.definition(magicKnivesEnchantable,
                                5,
                                1,
                                Enchantment.dynamicCost(5, 8),
                                Enchantment.dynamicCost(55, 8),
                                2,
                                EquipmentSlotGroup.ANY)
                )
                .withEffect(KillYoukaiEnchantmentEffectComponents.SCAVENGE_PROJECTILES, Unit.INSTANCE)
                .exclusiveWith(magicKnivesExclusive)
                .build(SCAVENGE.location());
        Enchantment timecollection = Enchantment.enchantment(
                        Enchantment.definition(magicKnivesEnchantable,
                                2,
                                1,
                                Enchantment.dynamicCost(25, 25),
                                Enchantment.dynamicCost(75, 25),
                                4,
                                EquipmentSlotGroup.ANY)
                )
                .withEffect(KillYoukaiEnchantmentEffectComponents.AUTOMATIC_SCAVENGE, Unit.INSTANCE)
                .exclusiveWith(magicKnivesExclusive)
                .build(TIMECOLLECTION.location());
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

        context.register(SCATTER, scatter);
        context.register(SCAVENGE, scavenge);
        context.register(TIMECOLLECTION, timecollection);
        context.register(TIMESTASIS, timestasis);
    }
}
