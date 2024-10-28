package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces$FirstFloorRoomCollection")
public class WoodlandMansionPiecesFirstFloorRoomCollectionMixin {
    @ModifyReturnValue(method = "get1x2SideEntrance", at = @At("RETURN"))
    private String killyoukaiwithknives$potentiallyGetKitchen(String original, RandomSource random, boolean isStairs) {
        if (!isStairs && (!KillYoukaiWithKnives.hasGeneratedKitchen && random.nextFloat() < 0.4F || random.nextFloat() < 0.01F)) {
            KillYoukaiWithKnives.hasGeneratedKitchen = true;
            return "killyoukaiwithknives:woodland_mansion/1x2_kitchen";
        }
        return original;
    }
}
