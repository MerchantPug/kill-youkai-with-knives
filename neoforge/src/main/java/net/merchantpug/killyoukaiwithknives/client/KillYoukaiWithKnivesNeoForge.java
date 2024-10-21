package net.merchantpug.killyoukaiwithknives.client;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.client.model.MagicKnifeModel;
import net.merchantpug.killyoukaiwithknives.client.renderer.MagicKnifeRenderer;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class KillYoukaiWithKnivesNeoForge {
    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = KillYoukaiWithKnives.MOD_ID, value = Dist.CLIENT)
    public static class ModEvents {
        @SubscribeEvent
        public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(MagicKnifeModel.LAYER_LOCATION, MagicKnifeModel::createBodyLayer);
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(KillYoukaiEntityTypes.MAGIC_KNIFE, MagicKnifeRenderer::new);
    }
}
