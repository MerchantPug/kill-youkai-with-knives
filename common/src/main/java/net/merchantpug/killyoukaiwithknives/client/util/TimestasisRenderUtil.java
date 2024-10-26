package net.merchantpug.killyoukaiwithknives.client.util;

import java.io.IOException;

import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.merchantpug.killyoukaiwithknives.mixin.accessor.client.ClientLevelAccessor;

import org.joml.Matrix4f;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexSorting;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec3;

public class TimestasisRenderUtil {
    private static Minecraft minecraft;
    private static ShaderInstance shaderInstance;
    private static RenderTarget auxTarget;
    private static RenderType renderType;
    private static MultiBufferSource.BufferSource source;

    public static RenderType getTimestasisRenderType() {
        return renderType;
    }

    public static void init() {
        minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();

        try {
            shaderInstance =
                new ShaderInstance(minecraft.getResourceManager(), "rendertype_killyoukaiwithknives_timestasis",
                    DefaultVertexFormat.POSITION_COLOR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        auxTarget = new TextureTarget(window.getWidth(), window.getHeight(), false, Minecraft.ON_OSX);
        auxTarget.setClearColor(0f, 0f, 0f, 0f);

        renderType = new RenderType("killyoukaiwithknives_timestasis", DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.QUADS, 256, false, false, () -> {
            // render using the newly copied framebuffer
            RenderSystem.setShader(() -> shaderInstance);
            RenderSystem.disableCull();
            RenderSystem.setShaderTexture(0, auxTarget.getColorTextureId());
        }, () -> {
            RenderSystem.enableCull();
        }) {
        };

        source = MultiBufferSource.immediate(new ByteBufferBuilder(256));
    }

    public static void renderTimestasisedAreas(ClientLevel level, PoseStack pose, Matrix4f projection,
                                               Matrix4f position, Camera camera) {
        ((ClientLevelAccessor) level).killyoukaiwithknives$invokeGetEntities()
            .get(EntityTypeTest.forClass(TimestasisEntity.class), entity -> {
                render(entity, level, pose, projection, position, camera);
                return AbortableIterationConsumer.Continuation.CONTINUE;
            });
    }

    private static void render(TimestasisEntity entity, ClientLevel level, PoseStack pose, Matrix4f projection,
                               Matrix4f position, Camera camera) {
        // blit the main buffer to our aux buffer
        resize(auxTarget);
        copy(minecraft.getMainRenderTarget(), auxTarget);

        RenderSystem.setProjectionMatrix(projection, VertexSorting.DISTANCE_TO_ORIGIN);

        pose.pushPose();
        pose.mulPose(position);
        Vec3 camPos = camera.getPosition();

        pose.translate(entity.getX() - camPos.x, entity.getY() - camPos.y, entity.getZ() - camPos.z);
        pose.scale(entity.getRadius(), entity.getRadius(), entity.getRadius());
        pose.translate(-0.5f, -0.5f, -0.5f);

        VertexConsumer consumer = source.getBuffer(getTimestasisRenderType());
        for (Direction face : Direction.values()) {
            quad(pose.last(), consumer, face);
        }

        source.endLastBatch();

        pose.popPose();
    }

    private static void quad(PoseStack.Pose pose, VertexConsumer consumer, Direction face) {
        float left = 0F;
        float bottom = 0F;
        float right = 1F;
        float top = 1F;
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

    private static void resize(RenderTarget target) {
        Window window = minecraft.getWindow();

        if (window.getWidth() != target.width || window.getHeight() != target.height) {
            target.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
        }
    }

    private static void copy(RenderTarget from, RenderTarget to) {
        to.bindWrite(false);

        Matrix4f projBackup = RenderSystem.getProjectionMatrix();
        VertexSorting sortingBackup = RenderSystem.getVertexSorting();
        from.blitToScreen(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), true);
        RenderSystem.setProjectionMatrix(projBackup, sortingBackup);

        minecraft.getMainRenderTarget().bindWrite(false);
    }
}
