package net.merchantpug.killyoukaiwithknives.mixin.accessor;

import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemFrame.class)
public interface ItemFrameAccessor {
    @Invoker("setRotation")
    void killyoukaiwithknives$invokeSetRotation(int rotation, boolean updateNeighbours);
}
