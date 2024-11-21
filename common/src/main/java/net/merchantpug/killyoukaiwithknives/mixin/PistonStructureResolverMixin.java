package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBaseBlock.class)
public class PistonStructureResolverMixin {
    @ModifyExpressionValue(method = "checkIfExtend", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/piston/PistonStructureResolver;resolve()Z"))
    private boolean killyoukaiwithknives$extendLater(boolean original, Level level, BlockPos pos) {
        if (!level.getEntitiesOfClass(TimestasisEntity.class, new AABB(pos)).isEmpty()) {
            KillYoukaiWithKnives.DELAYED_PISTONS.compute(pos, (pos1, integer) -> {
                if (integer == null)
                    return 0;
                return integer + 1;
            });
            if (KillYoukaiWithKnives.DELAYED_PISTONS.get(pos) == 2) {
                KillYoukaiWithKnives.DELAYED_PISTONS.remove(pos);
                return true;
            }
            return false;
        }
        if (KillYoukaiWithKnives.DELAYED_PISTONS.containsKey(pos)) {
            KillYoukaiWithKnives.DELAYED_PISTONS.remove(pos);
            return true;
        }
        return original;
    }
}
