package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WoodlandMansionPieces.class)
public class WoodlandMansionPiecesMixin {
    @Inject(method = "generateMansion", at = @At("HEAD"))
    private static void killyoukaiwithknives$hasGeneratedKitchen(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation, List<WoodlandMansionPieces.WoodlandMansionPiece> pieces, RandomSource random, CallbackInfo ci) {
        KillYoukaiWithKnives.hasGeneratedKitchen = false;
    }
}
