package net.merchantpug.killyoukaiwithknives;


import net.merchantpug.killyoukaiwithknives.platform.KillYoukaiWithKnivesPlatformHelperNeoForge;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@Mod(KillYoukaiWithKnives.MOD_ID)
public class KillYoukaiWithKnivesNeoForge {

    public KillYoukaiWithKnivesNeoForge(IEventBus eventBus) {
        KillYoukaiWithKnives.init();
        KillYoukaiWithKnives.setHelper(new KillYoukaiWithKnivesPlatformHelperNeoForge());
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
    }
}