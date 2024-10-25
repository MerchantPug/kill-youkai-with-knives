package net.merchantpug.killyoukaiwithknives.client.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.merchantpug.killyoukaiwithknives.mixin.accessor.client.ClientLevelAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.io.IOException;

public class TimestasisRenderUtil {
    private static RenderType renderType;

    public static RenderType getTimestasisRenderType() {
        return renderType;
    }

    public static void init() {
        Minecraft minecraft = Minecraft.getInstance();

        renderType = RenderType.create(
                "killyoukaiwithknives_timestasis",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                1536,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(() -> {
                            try {
                                return new ShaderInstance(minecraft.getResourceManager(), "rendertype_killyoukaiwithknives_timestasis", DefaultVertexFormat.POSITION_TEX);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }))
                        .setCullState(RenderType.NO_CULL)
                        .setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
                        .setOutputState(RenderStateShard.MAIN_TARGET)
                        .createCompositeState(false)
        );
    }

    // FIXME: Handle this, params can be changed.
    public static void renderTimestasisedAreas(ClientLevel level, PoseStack pose) {
        ((ClientLevelAccessor)level).killyoukaiwithknives$invokeGetEntities().get(EntityTypeTest.forClass(TimestasisEntity.class), entity -> {
            return AbortableIterationConsumer.Continuation.CONTINUE;
        });
    }

    private static void quad(PoseStack.Pose pose, VertexConsumer consumer, Direction face) {
        float left = -0.5F;
        float bottom = -0.5F;
        float right = 0.5F;
        float top = 0.5F;
        float depth = 0.0F;
        if (Math.abs(depth) < 0.00001F)
            depth = 0.0F;

        Vec3i normal = face.getNormal();

        switch (face) {
            case UP:
                depth = 1F - depth;
                top = 1F - top;
                bottom = 1F - bottom;
            case DOWN:
                vertex(pose, consumer, left, depth, top, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, left, depth, bottom, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, depth, bottom, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, depth, top, normal.getX(), normal.getY(), normal.getZ());
                return;

            case NORTH:
                depth = 1F - depth;
                left = 1F - left;
                right = 1F - right;
            case SOUTH:
                depth = 1F - depth;
                vertex(pose, consumer, left, top, depth, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, left, bottom, depth, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, bottom, depth, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, right, top, depth, normal.getX(), normal.getY(), normal.getZ());
                return;

            case EAST:
                depth = 1F - depth;
                left = 1F - left;
                right = 1F - right;
            case WEST:
                vertex(pose, consumer, depth, top, left, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, depth, bottom, left, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, depth, bottom, right, normal.getX(), normal.getY(), normal.getZ());
                vertex(pose, consumer, depth, top, right, normal.getX(), normal.getY(), normal.getZ());
        }
    }

    private static void vertex(
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
}
