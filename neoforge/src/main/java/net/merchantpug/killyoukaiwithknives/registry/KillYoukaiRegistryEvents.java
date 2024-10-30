package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.attribute.KillYoukaiAttributes;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantmentEffectComponents;
import net.merchantpug.killyoukaiwithknives.entity.KillYoukaiEntityTypes;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiArmorMaterials;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.sound.KillYoukaiSoundEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;

@EventBusSubscriber(modid = KillYoukaiWithKnives.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class KillYoukaiRegistryEvents {
    @SubscribeEvent
    public static void registerContent(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.ATTRIBUTE)
            KillYoukaiAttributes.registerAll();

        if (event.getRegistryKey() == Registries.ARMOR_MATERIAL) {
            KillYoukaiSoundEvents.registerAll();
            registerHolders(KillYoukaiArmorMaterials::registerAll);
            KillYoukaiItems.registerAll(Registry::register);
        }

        register(event, KillYoukaiAttachments::registerAll);
        register(event, KillYoukaiEnchantmentEffectComponents::registerAll);
        register(event, KillYoukaiEntityTypes::registerAll);
    }

    private static <T> void register(RegisterEvent event, Consumer<RegistrationCallback<T>> consumer) {
        consumer.accept((registry, id, value) ->
                event.register(registry.key(), id, () -> value));
    }

    private static <T> void registerHolders(Consumer<HolderRegistrationCallback<T>> consumer) {
        consumer.accept((registry, id, value) -> {
            Registry.register(registry, id, value);
            return DeferredHolder.create(registry.key(), id);
        });
    }
}
