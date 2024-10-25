package net.merchantpug.killyoukaiwithknives.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.merchantpug.killyoukaiwithknives.client.util.TimestasisRenderUtil;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;

public class TimestasisRenderer extends EntityRenderer<TimestasisEntity> {
    public TimestasisRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(TimestasisEntity entity, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffer, int packedLight) {
        VertexConsumer consumer = buffer.getBuffer(TimestasisRenderUtil.getTimestasisRenderType());
        
        for (Direction face : Direction.values()) {
            quad(pose.last(), consumer, face, 0, 0, 1, 1, 0);
        }

        super.render(entity, yaw, partialTick, pose, buffer, packedLight);
    }
    
    public void quad(PoseStack.Pose pose, VertexConsumer consumer, Direction face, float left, float bottom, float right, float top, float depth) {
        if (Math.abs(depth) < 0.00001f) {
            // cull face?
            depth = 0f;
        }
        
        // nominal face?
        
        Vec3i normal = face.getNormal();
        
        switch (face) {
            case UP:
                depth = 1f - depth;
                top = 1f - top;
                bottom = 1f - bottom;
            case DOWN:
                vertex(pose, consumer, left, depth, top, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, left, depth, bottom, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, depth, bottom, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, depth, top, normal.getX(), normal.getY(), normal.getZ());
                return;

            case NORTH:
                depth = 1f - depth;
                left = 1f - left;
                right = 1f - right;
            case SOUTH:
                depth = 1f - depth;
                vertex(pose, consumer, left, top, depth, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, left, bottom, depth, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, bottom, depth, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, top, depth, normal.getX(), normal.getY(), normal.getZ());
                return;

            case EAST:
                depth = 1f - depth;
                left = 1f - left;
                right = 1f - right;
            case WEST:
                vertex(pose, consumer, depth, top, left, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, depth, bottom, left, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, depth, bottom, right, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, depth, top, right, normal.getX(), normal.getY(), normal.getZ());
        }
    }

    public void vertex(
            PoseStack.Pose pose,
            VertexConsumer consumer,
            float x,
            float y,
            float z,
            float normalX,
            float normalY,
            float normalZ
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(0, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(15728880)
                .setNormal(pose, normalX, normalY, normalZ);
    }

    @Override
    public ResourceLocation getTextureLocation(TimestasisEntity entity) {
        return null;
    }
}
