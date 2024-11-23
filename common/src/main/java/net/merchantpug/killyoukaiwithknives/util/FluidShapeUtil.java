package net.merchantpug.killyoukaiwithknives.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FluidShapeUtil {

    public static final VoxelShape[] VOXEL_SHAPES;

    static {
        VOXEL_SHAPES = new VoxelShape[17];
        for(int i = 0; i < 17; i++) {
            VOXEL_SHAPES[i] = Block.box(0.0, 0.0, 0.0, 16.0, Math.max(i * 2, 16), 16.0);
        }
    }

    public static FluidState getFluidState(BlockGetter world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos)) {
            return Fluids.EMPTY.defaultFluidState();
        } else {
            if (world instanceof LevelReader worldView) {
                ChunkAccess chunk = worldView.getChunk(pos);
                return chunk.getFluidState(pos);
            }
            return world.getFluidState(pos);
        }
    }
}