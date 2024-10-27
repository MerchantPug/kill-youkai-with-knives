package net.merchantpug.killyoukaiwithknives.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantmentEffectComponents;
import net.merchantpug.killyoukaiwithknives.entity.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.merchantpug.killyoukaiwithknives.mixin.accessor.EnchantmentAccessor;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;

public record SummonTimestasisEffect(LevelBasedValue increasePerTick, LevelBasedValue maxScale, LevelBasedValue lifespan) {
    public static final Codec<SummonTimestasisEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            LevelBasedValue.CODEC.optionalFieldOf("increase_per_tick", LevelBasedValue.constant(0.1F)).forGetter(SummonTimestasisEffect::increasePerTick),
            LevelBasedValue.CODEC.fieldOf("max_scale").forGetter(SummonTimestasisEffect::maxScale),
            LevelBasedValue.CODEC.fieldOf("lifespan").forGetter(SummonTimestasisEffect::lifespan)
    ).apply(inst, SummonTimestasisEffect::new));

    public static void summonTimestasis(ServerLevel level, Projectile projectile, Entity target, DamageSource source) {
        if (projectile.getWeaponItem() == null)
            return;
        projectile.getWeaponItem().getEnchantments().entrySet().forEach((entry) -> {
            int enchantmentLevel = entry.getIntValue();
            LootContext context = Enchantment.damageContext(level, enchantmentLevel, target, source);
            Holder<Enchantment> enchantment = entry.getKey();
            if (!enchantment.isBound())
                return;
            EnchantmentAccessor.killyoukaiwithknives$invokeApplyEffects(enchantment.value().getEffects(KillYoukaiEnchantmentEffectComponents.SUMMON_TIMESTASIS), context, effect -> {
                TimestasisEntity entity = KillYoukaiEntityTypes.TIMESTASIS.create(level);
                if (entity != null) {
                    entity.setOwner(projectile.getOwner());
                    entity.setIncreasePerTick(effect.increasePerTick.calculate(enchantmentLevel));
                    entity.setMaxSize(effect.maxScale.calculate(enchantmentLevel));
                    entity.setLifespan(entity.tickCount + (long)effect.lifespan.calculate(enchantmentLevel));
                    entity.setPos(target.position().add(0, entity.getBbHeight() * 0.5, 0));
                    level.addFreshEntity(entity);
                }
            });
        });
    }
}
