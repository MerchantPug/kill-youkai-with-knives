package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemplateStructurePiece.class)
public abstract class TemplateStructurePieceMixin extends StructurePiece {
    @Shadow @Final protected String templateName;

    protected TemplateStructurePieceMixin(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
        super(type, genDepth, boundingBox);
    }

    @Inject(method = "makeTemplateLocation", at = @At("HEAD"), cancellable = true)
    private void killyoukaiwithknives$modifyTemplateLocation(CallbackInfoReturnable<ResourceLocation> cir) {
        if (getType() == StructurePieceType.WOODLAND_MANSION_PIECE && templateName.equals("killyoukaiwithknives:woodland_mansion/1x2_kitchen"))
            cir.setReturnValue(KillYoukaiWithKnives.asResource("woodland_mansion/1x2_kitchen"));
    }
}
