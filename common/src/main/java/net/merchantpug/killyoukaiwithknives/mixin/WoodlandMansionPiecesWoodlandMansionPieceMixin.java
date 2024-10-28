package net.merchantpug.killyoukaiwithknives.mixin;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.mixin.accessor.ItemFrameAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WoodlandMansionPieces.WoodlandMansionPiece.class)
public abstract class WoodlandMansionPiecesWoodlandMansionPieceMixin extends TemplateStructurePiece {
    public WoodlandMansionPiecesWoodlandMansionPieceMixin(StructurePieceType type, int genDepth, StructureTemplateManager structureTemplateManager, ResourceLocation location, String templateName, StructurePlaceSettings placeSettings, BlockPos templatePosition) {
        super(type, genDepth, structureTemplateManager, location, templateName, placeSettings, templatePosition);
    }

    @Inject(method = "makeLocation", at = @At("HEAD"), cancellable = true)
    private static void killyoukaiwithknives$potentiallyGetKitchen(String name, CallbackInfoReturnable<ResourceLocation> cir) {
        if (name.equals("killyoukaiwithknives:woodland_mansion/1x2_kitchen"))
            cir.setReturnValue(KillYoukaiWithKnives.asResource("woodland_mansion/1x2_kitchen"));
    }

    @Inject(method = "handleDataMarker", at = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>()V"), cancellable = true)
    private void killyoukaiwithknives$addMaidArmorStands(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box, CallbackInfo ci) {
        if (name.startsWith("Kill Youkai With Knives")) {
            Rotation rotation = placeSettings.getRotation();
            switch (name) {
                case "Kill Youkai With Knives Maid Armor Stand West" -> {
                    ArmorStand armorStand = EntityType.ARMOR_STAND.create(level.getLevel());
                    if (armorStand != null) {
                        armorStand.setItemSlot(EquipmentSlot.HEAD, new ItemStack(KillYoukaiItems.MAID_BONNET));
                        armorStand.setItemSlot(EquipmentSlot.CHEST, new ItemStack(KillYoukaiItems.MAID_DRESS));
                        armorStand.setItemSlot(EquipmentSlot.LEGS, new ItemStack(KillYoukaiItems.MAID_LEGGINGS));
                        armorStand.setItemSlot(EquipmentSlot.FEET, new ItemStack(KillYoukaiItems.MAID_BOOTS));
                        armorStand.moveTo(pos, RotationSegment.convertToDegrees(rotation.rotate(4,16)), 0.0F);
                        level.addFreshEntityWithPassengers(armorStand);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                        ci.cancel();
                    }
                }
                case "Kill Youkai With Knives Maid Armor Stand North West" -> {
                    ArmorStand armorStand = EntityType.ARMOR_STAND.create(level.getLevel());
                    if (armorStand != null) {
                        armorStand.setItemSlot(EquipmentSlot.HEAD, new ItemStack(KillYoukaiItems.MAID_BONNET));
                        armorStand.setItemSlot(EquipmentSlot.CHEST, new ItemStack(KillYoukaiItems.MAID_DRESS));
                        armorStand.setItemSlot(EquipmentSlot.LEGS, new ItemStack(KillYoukaiItems.MAID_LEGGINGS));
                        armorStand.setItemSlot(EquipmentSlot.FEET, new ItemStack(KillYoukaiItems.MAID_BOOTS));
                        armorStand.moveTo(pos, RotationSegment.convertToDegrees(rotation.rotate(6,16)), 0.0F);
                        level.addFreshEntityWithPassengers(armorStand);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                        ci.cancel();
                    }
                }
                case "Kill Youkai With Knives Item Frame" -> {
                    ItemFrame frame = new ItemFrame(level.getLevel(), pos, Direction.UP);
                    // Don't update neighbors otherwise the game will have a chance to hang. Fun.
                    frame.setItem(new ItemStack(KillYoukaiItems.MAGIC_KNIVES), false);
                    frame.moveTo(pos, RotationSegment.convertToDegrees(rotation.rotate(0,16)), -90.0F);
                    ((ItemFrameAccessor)frame).killyoukaiwithknives$invokeSetRotation(rotation.rotate(4,8), false);
                    level.addFreshEntityWithPassengers(frame);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                    ci.cancel();
                }
            }
        }
    }
}
