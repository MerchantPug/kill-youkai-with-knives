package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Level level();

    @ModifyReturnValue(method = "getDeltaMovement", at = @At("RETURN"))
    private Vec3 killyoukaiwithknives$cancelProjectileDeltaMovementInTimestasis(Vec3 original) {
        if ((Entity)(Object)this instanceof AbstractArrow && KillYoukaiWithKnives.getHelper().isTimestasised((Entity)(Object)this))
            return Vec3.ZERO;
        return original;
    }
}
