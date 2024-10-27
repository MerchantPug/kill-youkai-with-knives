package net.merchantpug.killyoukaiwithknives.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.client.model.MagicKnifeModel;
import net.merchantpug.killyoukaiwithknives.client.renderer.MagicKnifeRenderer;
import net.merchantpug.killyoukaiwithknives.client.renderer.MaidArmorRenderer;
import net.merchantpug.killyoukaiwithknives.client.renderer.TimestasisRenderer;
import net.merchantpug.killyoukaiwithknives.client.util.TimestasisRenderUtil;
import net.merchantpug.killyoukaiwithknives.entity.KillYoukaiEntityTypes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.network.clientbound.SyncTimestasisStateClientboundPacket;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class KillYoukaiWithKnivesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(MagicKnifeModel.LAYER_LOCATION, MagicKnifeModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(MaidArmorRenderer.BONNET_DRESS_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.52F)), 64, 32));
        EntityModelLayerRegistry.registerModelLayer(MaidArmorRenderer.LEGGINGS_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.26F)), 64, 32));
        EntityModelLayerRegistry.registerModelLayer(MaidArmorRenderer.BOOTS_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.52F)), 64, 32));

        EntityRendererRegistry.register(KillYoukaiEntityTypes.MAGIC_KNIFE, MagicKnifeRenderer::new);
        EntityRendererRegistry.register(KillYoukaiEntityTypes.TIMESTASIS, TimestasisRenderer::new);

        ArmorRenderer.register(MaidArmorRenderer::render, KillYoukaiItems.MAID_BONNET, KillYoukaiItems.MAID_DRESS, KillYoukaiItems.MAID_LEGGINGS, KillYoukaiItems.MAID_BOOTS);

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> TimestasisRenderUtil.init());
        WorldRenderEvents.END.register(
            context -> TimestasisRenderUtil.renderTimestasisedAreas(context.world(), context.matrixStack(),
                context.projectionMatrix(), context.positionMatrix(), context.camera()));
        registerPackets();
    }

    private static void registerPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SyncTimestasisStateClientboundPacket.TYPE, (packet, context) -> packet.handle());
    }
}
