package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.KillYoukaiTags;
import net.merchantpug.killyoukaiwithknives.entity.MagicKnifeEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MagicKnivesItem extends ProjectileWeaponItem {
    public MagicKnivesItem(Properties properties) {
        super(properties);
    }

    public static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, 1.0F, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(BASE_ATTACK_SPEED_ID, -0.75F, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(KillYoukaiItems.MAGIC_KNIVES);
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.isCreative();
    }

    @Override
    public int getDefaultProjectileRange() {
        return 8;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(KillYoukaiTags.Items.MAGIC_KNIVES_REPAIR_INGREDIENT);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            ServerLevel serverLevel = (ServerLevel) level;
            List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < 3; ++i)
                if (stack.getDamageValue() < stack.getMaxDamage() - i - 1 || stack.getDamageValue() == stack.getMaxDamage() - 1)
                    stacks.add(new ItemStack(KillYoukaiItems.MAGIC_KNIVES));

            int projectileCount = EnchantmentHelper.processProjectileCount(serverLevel, stack, player, 0);
            for (int i = 0; i < projectileCount; ++i) {
                if (stack.getDamageValue() < stack.getMaxDamage() - i - 1)
                    stacks.add(new ItemStack(KillYoukaiItems.MAGIC_KNIVES));
            }

            shoot(serverLevel, player, player.getUsedItemHand(), stack, stacks, 2.0F, 8.0F, false, null);
            // FIXME: Change sound!
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() * 0.4F + 1.0F);
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
        projectile.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot() + angle, 0.0F, velocity, inaccuracy);
    }

}
