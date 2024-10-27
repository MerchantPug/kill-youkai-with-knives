package net.merchantpug.killyoukaiwithknives.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.item.MaidArmorItem;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class MaidArmorRenderer {
    public static final ModelLayerLocation BONNET_DRESS_LOCATION = new ModelLayerLocation(KillYoukaiWithKnives.asResource("maid"), "bonnet_dress");
    public static final ModelLayerLocation LEGGINGS_LOCATION = new ModelLayerLocation(KillYoukaiWithKnives.asResource("maid"), "leggings");
    public static final ModelLayerLocation BOOTS_LOCATION = new ModelLayerLocation(KillYoukaiWithKnives.asResource("maid"), "boots");

    private static HumanoidArmorModel<LivingEntity> bonnetDressModel;
    private static HumanoidArmorModel<LivingEntity> leggingsModel;
    private static HumanoidArmorModel<LivingEntity> bootsModel;

    public static void initModels(EntityRendererProvider.Context context) {
        bonnetDressModel = new HumanoidArmorModel<>(context.bakeLayer(BONNET_DRESS_LOCATION));
        leggingsModel = new HumanoidArmorModel<>(context.bakeLayer(LEGGINGS_LOCATION));
        bootsModel = new HumanoidArmorModel<>(context.bakeLayer(BOOTS_LOCATION));
    }

    public static HumanoidArmorModel<?> setupGetModel(EquipmentSlot slot) {
        bonnetDressModel.setAllVisible(false);
        leggingsModel.setAllVisible(false);
        bootsModel.setAllVisible(false);
        switch (slot) {
            case HEAD: {
                bonnetDressModel.head.visible = true;
                return bonnetDressModel;
            }
            case CHEST: {
                bonnetDressModel.body.visible = true;
                bonnetDressModel.leftArm.visible = true;
                bonnetDressModel.rightArm.visible = true;
                bonnetDressModel.leftLeg.visible = true;
                bonnetDressModel.rightLeg.visible = true;
                return bonnetDressModel;
            }
            case LEGS: {
                leggingsModel.leftLeg.visible = true;
                leggingsModel.rightLeg.visible = true;
                return leggingsModel;
            }
            case FEET: {
                bootsModel.leftLeg.visible = true;
                bootsModel.rightLeg.visible = true;
                return bootsModel;
            }
            default: {}
        }
        return null;
    }

    public static void render(PoseStack pose, MultiBufferSource buffers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<?> contextModel) {
        HumanoidArmorModel<?> model = setupGetModel(slot);
        render(pose, buffers, stack, entity, light, model, contextModel, slot);
    }

    private static void render(PoseStack pose, MultiBufferSource buffers, ItemStack stack, LivingEntity entity, int light, HumanoidModel<?> model, HumanoidModel<?> contextModel, EquipmentSlot slot) {
        if (stack.getItem() instanceof MaidArmorItem armor && slot == armor.getEquipmentSlot()) {
            contextModel.copyPropertiesTo((HumanoidModel) model);
            model.renderToBuffer(pose, buffers.getBuffer(RenderType.armorCutoutNoCull(armor.getArmorTexture(stack, entity, slot, null, false))), light, OverlayTexture.NO_OVERLAY);
        }
    }
}
