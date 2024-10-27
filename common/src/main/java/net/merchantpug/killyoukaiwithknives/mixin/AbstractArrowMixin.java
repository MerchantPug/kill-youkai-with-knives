package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.merchantpug.killyoukaiwithknives.entity.MagicKnifeEntity;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    @Unique
    @Nullable
    private Vec3 killyoukaiwithknives$previousDeltaMovement = null;

    public AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getX()D", ordinal = 1), cancellable = true)
    private void killyoukaiwithknives$cancelProjectileMovementInTimestasis(CallbackInfo ci) {
        if ((AbstractArrow)(Object)this instanceof MagicKnifeEntity magicKnife && !magicKnife.affectedByTimestasis)
            return;

        if (!level().getEntitiesOfClass(TimestasisEntity.class, getBoundingBox()).isEmpty()) {
            if (killyoukaiwithknives$previousDeltaMovement == null) {
                killyoukaiwithknives$previousDeltaMovement = getDeltaMovement();
            }
            xRotO = getXRot();
            yRotO = getYRot();
            setDeltaMovement(Vec3.ZERO);
            ci.cancel();
        }
    }

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private Vec3 killyoukaiwithknives$returnMovementToNormal(Vec3 original, @Local float f) {
        if (killyoukaiwithknives$previousDeltaMovement != null) {
            Vec3 deltaUpdate = killyoukaiwithknives$previousDeltaMovement;
            killyoukaiwithknives$previousDeltaMovement = null;
            return deltaUpdate.scale(f);
        }
        return original;
    }
}
