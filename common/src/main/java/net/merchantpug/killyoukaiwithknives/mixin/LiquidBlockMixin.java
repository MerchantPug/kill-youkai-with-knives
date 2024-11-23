package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.util.EntityGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {
    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$dontSpreadLiquid(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!EntityGetter.getTimestasisEntities(level, pos).isEmpty())
            cir.setReturnValue(false);
    }
}
