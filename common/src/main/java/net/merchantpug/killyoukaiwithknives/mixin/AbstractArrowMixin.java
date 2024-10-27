package net.merchantpug.killyoukaiwithknives.mixin;

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
    @Unique
    private boolean killyoukaiwithknives$updateDelta = false;

    public AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getX()D", ordinal = 1), cancellable = true)
    private void killyoukaiwithknives$cancelProjectileMovementInTimestasis(CallbackInfo ci) {
        if ((AbstractArrow)(Object)this instanceof MagicKnifeEntity magicKnife && !magicKnife.affectedByTimestasis)
            return;

        if (!level().getEntitiesOfClass(TimestasisEntity.class, getBoundingBox()).isEmpty()) {
            if (killyoukaiwithknives$previousDeltaMovement == null)
                killyoukaiwithknives$previousDeltaMovement = getDeltaMovement();
            setDeltaMovement(0, 0, 0);
            yRotO = getYRot();
            xRotO = getXRot();
            ci.cancel();
        } else if (killyoukaiwithknives$previousDeltaMovement != null)
            killyoukaiwithknives$updateDelta = true;
    }

    @ModifyArg(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private Vec3 killyoukaiwithknives$returnMovementToNormal(Vec3 original) {
        if (killyoukaiwithknives$updateDelta) {
            Vec3 deltaUpdate = killyoukaiwithknives$previousDeltaMovement;
            killyoukaiwithknives$previousDeltaMovement = null;
            killyoukaiwithknives$updateDelta = false;
            return deltaUpdate;
        }
        return original;
    }

    @Inject(method = "startFalling", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$cancelFalling(CallbackInfo ci) {
        if (killyoukaiwithknives$previousDeltaMovement != null)
            ci.cancel();
    }
}
