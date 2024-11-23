package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.util.EntityGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidState.class)
public class FluidStateMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$freezeFluidTick(Level level, BlockPos pos, CallbackInfo ci) {
        if (!EntityGetter.getTimestasisEntities(level, pos).isEmpty())
            ci.cancel();
    }

    @Inject(method = "animateTick", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$freezeFluidAnimate(Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!EntityGetter.getTimestasisEntities(level, pos).isEmpty())
            ci.cancel();
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$freezeFluidRandomTick(Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (!EntityGetter.getTimestasisEntities(level, pos).isEmpty())
            ci.cancel();
    }
}
