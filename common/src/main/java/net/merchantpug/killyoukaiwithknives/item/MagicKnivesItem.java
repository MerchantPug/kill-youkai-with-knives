package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.entity.MagicKnifeEntity;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MagicKnivesItem extends ProjectileWeaponItem {
    public MagicKnivesItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(KillYoukaiItems.MAGIC_KNIVES);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 8;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            ServerLevel serverLevel = (ServerLevel) level;
            List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < 4; ++i)
                stacks.add(new ItemStack(KillYoukaiItems.MAGIC_KNIVES));
            shoot(serverLevel, player, player.getUsedItemHand(), stack, stacks, 4.0F, 0.0F, true, null);
            // FIXME: Change sound!
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F));
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(this, 20);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 1;
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        return new MagicKnifeEntity(shooter, level, weapon);
    }

    @Override
    protected void shootProjectile(LivingEntity livingEntity, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity livingEntity1) {
        projectile.setPos(projectile.position().offsetRandom(projectile.getRandom(), 1.0f));
        projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot() + angle, 0.0F, velocity, inaccuracy);
    }

}
