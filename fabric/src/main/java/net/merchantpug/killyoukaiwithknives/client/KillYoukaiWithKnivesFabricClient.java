package net.merchantpug.killyoukaiwithknives.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.merchantpug.killyoukaiwithknives.client.model.MagicKnifeModel;
import net.merchantpug.killyoukaiwithknives.client.renderer.MagicKnifeRenderer;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiEntityTypes;

public class KillYoukaiWithKnivesFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(MagicKnifeModel.LAYER_LOCATION, MagicKnifeModel::createBodyLayer);
        EntityRendererRegistry.register(KillYoukaiEntityTypes.MAGIC_KNIFE, MagicKnifeRenderer::new);
    }
}
