package net.merchantpug.killyoukaiwithknives.client;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.client.model.MagicKnifeModel;
import net.merchantpug.killyoukaiwithknives.client.renderer.MagicKnifeRenderer;
import net.merchantpug.killyoukaiwithknives.client.renderer.MaidArmorRenderer;
import net.merchantpug.killyoukaiwithknives.client.renderer.TimestasisRenderer;
import net.merchantpug.killyoukaiwithknives.client.util.TimestasisRenderUtil;
import net.merchantpug.killyoukaiwithknives.entity.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class KillYoukaiWithKnivesNeoForgeClient {
    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = KillYoukaiWithKnives.MOD_ID, value = Dist.CLIENT)
    public static class ModEvents {
        @SubscribeEvent
        public static void onClientSetup(RegisterShadersEvent event) {
            TimestasisRenderUtil.init();
        }

        @SubscribeEvent
        public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(MagicKnifeModel.LAYER_LOCATION, MagicKnifeModel::createBodyLayer);
            event.registerLayerDefinition(MaidArmorRenderer.BONNET_DRESS_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.4F)), 64, 32));
            event.registerLayerDefinition(MaidArmorRenderer.LEGGINGS_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.2F)), 64, 32));
            event.registerLayerDefinition(MaidArmorRenderer.BOOTS_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(0.4F)), 64, 32));
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(KillYoukaiEntityTypes.MAGIC_KNIFE, MagicKnifeRenderer::new);
            event.registerEntityRenderer(KillYoukaiEntityTypes.TIMESTASIS, TimestasisRenderer::new);
        }

        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            MaidArmorRenderer.initModels(event.getContext());
        }

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerItem(new IClientItemExtensions() {
                @Override
                public HumanoidModel<?> getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
                    HumanoidArmorModel<?> armorModel = MaidArmorRenderer.setupGetModel(slot);
                    original.copyPropertiesTo((HumanoidModel) armorModel);
                    return armorModel;
                }
            }, KillYoukaiItems.MAID_BONNET, KillYoukaiItems.MAID_DRESS, KillYoukaiItems.MAID_LEGGINGS, KillYoukaiItems.MAID_BOOTS);
        }
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = KillYoukaiWithKnives.MOD_ID, value = Dist.CLIENT)
    public static class GameEvents {
        @SubscribeEvent
        public static void onRenderLevel(RenderLevelStageEvent event) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL)
                TimestasisRenderUtil.renderTimestasisedAreas(Minecraft.getInstance().level, event.getPoseStack(),
                    event.getProjectionMatrix(), event.getModelViewMatrix(), event.getCamera());
        }
    }
}
