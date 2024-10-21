package net.merchantpug.killyoukaiwithknives.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.client.model.MagicKnifeModel;
import net.merchantpug.killyoukaiwithknives.entity.MagicKnifeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MagicKnifeRenderer extends EntityRenderer<MagicKnifeEntity> {
    private final MagicKnifeModel model;

    public MagicKnifeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MagicKnifeModel(context.bakeLayer(MagicKnifeModel.LAYER_LOCATION));
    }

    public void render(MagicKnifeEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource source, int packedLight) {
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) + 90.0F));
        stack.mulPose(Axis.ZN.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) + 185.0F));
        model.renderToBuffer(stack, source.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY);
        stack.popPose();
        super.render(entity, entityYaw, partialTicks, stack, source, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MagicKnifeEntity entity) {
        return KillYoukaiWithKnives.asResource("textures/entity/magic_knife.png");
    }
}
