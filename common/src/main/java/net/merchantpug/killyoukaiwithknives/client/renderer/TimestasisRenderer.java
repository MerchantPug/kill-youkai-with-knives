package net.merchantpug.killyoukaiwithknives.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TimestasisRenderer extends EntityRenderer<TimestasisEntity> {
    public TimestasisRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TimestasisEntity entity, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffer, int packedLight) {
        // No-op
    }

    @Override
    public ResourceLocation getTextureLocation(TimestasisEntity entity) {
        return null;
    }
}
