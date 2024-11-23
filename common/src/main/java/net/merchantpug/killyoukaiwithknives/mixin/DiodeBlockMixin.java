package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.util.EntityGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DiodeBlock.class)
public class DiodeBlockMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$dontTickRedstone(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        var entities = EntityGetter.getTimestasisEntities(level, pos);
        if (!entities.isEmpty()) {
            if (!level.getBlockTicks().hasScheduledTick(pos, state.getBlock()))
                level.scheduleTick(pos, state.getBlock(), entities.stream().map(timestasisEntity -> (int)timestasisEntity.getLifespan() - timestasisEntity.tickCount).max(Integer::compare).orElse(0), TickPriority.VERY_HIGH);
            ci.cancel();
        }
    }
}
