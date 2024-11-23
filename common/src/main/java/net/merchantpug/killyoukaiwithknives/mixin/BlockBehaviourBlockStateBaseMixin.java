package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.util.EntityGetter;
import net.merchantpug.killyoukaiwithknives.util.FluidShapeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockBehaviourBlockStateBaseMixin {
    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"))
    private VoxelShape killyoukaiwithknives$updateFluidShapeIfTimestasised(VoxelShape original, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (
                level instanceof Level lvl &&
                        !EntityGetter.getTimestasisEntities(lvl, pos).isEmpty() &&
                        (context instanceof EntityCollisionContext collisionContext && collisionContext.getEntity() != null && !collisionContext.getEntity().isInWaterOrBubble()) &&
                        !FluidShapeUtil.getFluidState(lvl, pos).isEmpty()
        )
            return Shapes.join(FluidShapeUtil.VOXEL_SHAPES[Mth.clamp(FluidShapeUtil.getFluidState(lvl, pos).getAmount(), 0, 16)], original, BooleanOp.OR);
        return original;
    }
}
