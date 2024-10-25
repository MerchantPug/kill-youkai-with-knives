package net.merchantpug.killyoukaiwithknives.client.util;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

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

    public static void render() {

    }
}
