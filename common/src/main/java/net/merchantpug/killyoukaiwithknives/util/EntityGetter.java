package net.merchantpug.killyoukaiwithknives.util;

import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class EntityGetter {
    public static List<TimestasisEntity> getTimestasisEntities(Level level, BlockPos pos) {
        return level.getEntitiesOfClass(TimestasisEntity.class, new AABB(pos));
    }
}
