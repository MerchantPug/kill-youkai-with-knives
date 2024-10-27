package net.merchantpug.killyoukaiwithknives;


import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.network.clientbound.SyncTimestasisStateClientboundPacket;
import net.merchantpug.killyoukaiwithknives.platform.KillYoukaiWithKnivesPlatformHelperNeoForge;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.List;
import java.util.Set;

@Mod(KillYoukaiWithKnives.MOD_ID)
public class KillYoukaiWithKnivesNeoForge {

    public KillYoukaiWithKnivesNeoForge(IEventBus eventBus) {
        KillYoukaiWithKnives.init();
        KillYoukaiWithKnives.setHelper(new KillYoukaiWithKnivesPlatformHelperNeoForge());
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = KillYoukaiWithKnives.MOD_ID)
    public static class ModEvents {
        @SubscribeEvent
        public static void registerPackets(RegisterPayloadHandlersEvent event) {
            event.registrar("1.0.0")
                    .playToClient(SyncTimestasisStateClientboundPacket.TYPE, SyncTimestasisStateClientboundPacket.STREAM_CODEC, (packet, context) -> packet.handle());
        }

        @SubscribeEvent
        public static void onBuildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.COMBAT) {
                event.insertAfter(new ItemStack(Items.MACE), new ItemStack(KillYoukaiItems.MAGIC_KNIVES), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                insertAfter(event.getParentEntries(), Items.TURTLE_HELMET, List.of(KillYoukaiItems.MAID_BONNET, KillYoukaiItems.MAID_DRESS, KillYoukaiItems.MAID_LEGGINGS, KillYoukaiItems.MAID_BOOTS), event::insertAfter);
            }
        }

        private static void insertAfter(Set<ItemStack> stacks, Item start, List<Item> itemsToAdd, AddAfterOperation operation) {
            ItemStack startItem = null;
            for (ItemStack entry : stacks) {
                if (entry.is(start)) {
                    startItem = entry;
                    break;
                }
            }
            if (startItem != null) {
                for (Item item : itemsToAdd) {
                    ItemStack stack = new ItemStack(item);
                    operation.insertAfter(startItem, stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    startItem = stack;
                }
            }
        }

        @FunctionalInterface
        private interface AddAfterOperation {
            void insertAfter(ItemStack startItem, ItemStack afterItem, CreativeModeTab.TabVisibility visibility);
        }
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = KillYoukaiWithKnives.MOD_ID)
    public static class GameEvents {
        @SubscribeEvent
        public static void onLivingDamagePost(LivingDamageEvent.Post event) {
            Entity entity = event.getEntity();
            DamageSource source = event.getSource();

            if (source.is(KillYoukaiTags.DamageTypes.KNIVES_BYPASS_COOLDOWN_AFTER) && source.getEntity() != null)
                entity.setData(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER, source.getEntity().getUUID());
            else
                entity.removeData(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER);
        }

        @SubscribeEvent
        public static void onEntityTick(EntityTickEvent.Post event) {
            TimestasisEntity.runEntityLogic(event.getEntity());
        }
    }
}