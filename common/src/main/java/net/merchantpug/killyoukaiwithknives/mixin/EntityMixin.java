package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Level level();

    @Shadow public abstract AABB getBoundingBox();

    @Shadow public float xRotO;

    @Shadow public abstract float getXRot();

    @Shadow public abstract float getYRot();

    @Shadow public float yRotO;

    @Unique
    @Nullable
    private Vec3 killyoukaiwithknives$previousPosition = null;

    @ModifyReturnValue(method = "trackingPosition", at = @At("RETURN"))
    private Vec3 killyoukaiwithknives$dontUpdateTrackingPosition(Vec3 original) {
        if ((Entity)(Object)this instanceof AbstractArrow && !level().getEntitiesOfClass(TimestasisEntity.class, getBoundingBox()).isEmpty()) {
            if (killyoukaiwithknives$previousPosition == null) {
                killyoukaiwithknives$previousPosition = original;
            }
            return killyoukaiwithknives$previousPosition;
        }
        killyoukaiwithknives$previousPosition = null;
        return original;
    }
}
