package net.merchantpug.killyoukaiwithknives.entity;

import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class MagicKnifeEntity extends AbstractArrow {
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
        float f = 8.0F;
        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, entity1 == null ? this : entity);
        if (this.level() instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, getWeaponItem(), entity, damagesource, f);
        }

        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damagesource, this.getWeaponItem());
            }

            if (entity instanceof LivingEntity livingentity) {
                doKnockback(livingentity, damagesource);
                doPostHurtEffects(livingentity);
            }
        }

        setDeltaMovement(getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    public void playerTouch(Player entity) {
        if (!this.level().isClientSide && (this.inGround || this.isNoPhysics()) && this.shakeTime <= 0)
            tryRepairKnivesInInventory(entity);
    }

    private boolean tryRepairKnivesInInventory(LivingEntity living) {
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

    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(KillYoukaiItems.MAGIC_KNIVES);
    }
}