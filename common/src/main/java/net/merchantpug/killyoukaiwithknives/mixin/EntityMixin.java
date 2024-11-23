package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.util.EntityGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Level level();

    @Shadow private Vec3 deltaMovement;

    @ModifyReturnValue(method = "getDeltaMovement", at = @At("RETURN"))
    private Vec3 killyoukaiwithknives$cancelProjectileDeltaMovementInTimestasis(Vec3 original) {
        if (((Entity)(Object)this instanceof Projectile && KillYoukaiWithKnives.getHelper().isTimestasised((Entity)(Object)this)))
            return Vec3.ZERO;
        return original;
    }

    @ModifyVariable(method = "saveWithoutId", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Entity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 killyoukaiwithknives$saveActualVec3WhenTimestasised(Vec3 value) {
        if ((Entity)(Object)this instanceof Projectile && KillYoukaiWithKnives.getHelper().isTimestasised((Entity)(Object)this))
            return deltaMovement;
        return value;
    }
}
