package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.entity.MagicKnifeEntity;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile {
    @Shadow public abstract void lerpTo(double x, double y, double z, float yRot, float xRot, int steps);

    @Unique
    @Nullable
    private Vec3 killyoukaiwithknives$previousPosition = null;

    public AbstractArrowMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getX()D", ordinal = 1), cancellable = true)
    private void killyoukaiwithknives$cancelProjectileMovementInTimestasis(CallbackInfo ci) {
        if (KillYoukaiWithKnives.getHelper().isTimestasised(this)) {
            if (killyoukaiwithknives$previousPosition == null) {
                Vec3 pos =  KillYoukaiWithKnives.getHelper().getTimestasisPos(this);
                killyoukaiwithknives$previousPosition = pos != null ? pos : position();
            }
            setPos(killyoukaiwithknives$previousPosition);
            xRotO = getXRot();
            yRotO = getYRot();
            ci.cancel();
        } else
            killyoukaiwithknives$previousPosition = null;
    }
}
