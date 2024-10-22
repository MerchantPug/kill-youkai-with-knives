package net.merchantpug.killyoukaiwithknives.entity;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.mixin.accessor.ProjectileAccessor;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class MagicKnifeEntity extends AbstractArrow {
    private boolean hasHitOwner = false;

    public MagicKnifeEntity(EntityType<MagicKnifeEntity> entityType, Level level) {
        super(entityType, level);
    }

    public MagicKnifeEntity(LivingEntity owner, Level level, ItemStack pickupItemStack) {
        super(KillYoukaiEntityTypes.MAGIC_KNIFE, owner, level, pickupItemStack, null);
        pickup = Pickup.DISALLOWED;
    }

    @Override
    protected double getDefaultGravity() {
        return 0.02;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide && getDeltaMovement().lengthSqr() < 1.0E-7 && result.getEntity() instanceof LivingEntity living && tryRepairKnivesInInventory(living))
            return;

        Entity entity = result.getEntity();
        float f = 4.0F;
        Entity owner = getOwner();

        if (entity == owner && ((ProjectileAccessor)this).killyoukaiwithknives$hasLeftOwner()) {
            if (owner instanceof LivingEntity living && !hasHitOwner) {
                tryRepairKnivesInInventory(living);
                hasHitOwner = true;
            }
            return;
        }

        DamageSource damageSource = damageSources().source(KillYoukaiDamageTypes.MAGIC_KNIVES, this, owner == null ? this : owner);
        if (KillYoukaiWithKnives.getHelper().previouslyHurtByKnives(entity, owner))
            damageSource = damageSources().source(KillYoukaiDamageTypes.COOLDOWN_BYPASSING_MAGIC_KNIVES, this, owner);

        if (level() instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, getWeaponItem(), entity, damageSource, f);
        }

        if (entity.hurt(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                doKnockback(livingentity, damageSource);
                doPostHurtEffects(livingentity);
            }
        }

        setDeltaMovement(getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    public ItemStack getWeaponItem() {
        return getPickupItemStackOrigin();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(KillYoukaiItems.MAGIC_KNIVES);
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player entity) {
        if (!level().isClientSide && (inGround || isNoPhysics()) && shakeTime <= 0)
            tryRepairKnivesInInventory(entity);
    }

    private boolean tryRepairKnivesInInventory(LivingEntity living) {
        if (!living.is(getOwner()))
            return false;
        for (ItemStack stack : living.getAllSlots()) {
            if (!stack.isEmpty() && stack.isDamaged() && ItemStack.isSameItem(new ItemStack(KillYoukaiItems.MAGIC_KNIVES), stack)) {
                // TODO: Change the sound to a new one.
                level().playSound(null, living, SoundEvents.ITEM_PICKUP, living.getSoundSource(),1.0F, 1.2F - living.getRandom().nextFloat() * 0.6F);
                discard();
                if (!(living instanceof Player player) || !player.isCreative())
                    stack.setDamageValue(stack.getDamageValue() - 1);
                return true;
            }
        }
        return false;
    }
}