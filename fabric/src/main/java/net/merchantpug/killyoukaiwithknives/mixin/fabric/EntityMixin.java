package net.merchantpug.killyoukaiwithknives.mixin.fabric;

import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void killyoukaiwithknives$runTimestasisLogic(CallbackInfo ci) {
        TimestasisEntity.runEntityLogic((Entity)(Object)this);
    }
}
