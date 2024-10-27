package net.merchantpug.killyoukaiwithknives.datagen;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.merchantpug.killyoukaiwithknives.KillYoukaiTags;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.damage.KillYoukaiDamageTypes;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantments;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

public class KillYoukaiWithKnivesDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(DynamicRegistryProvider::new);
        pack.addProvider(DamageTypeTagProvider::new);
        pack.addProvider(EnchantmentTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.DAMAGE_TYPE, KillYoukaiDamageTypes::bootstrap);
        registryBuilder.add(Registries.ENCHANTMENT, KillYoukaiEnchantments::bootstrap);
    }

    public static class DynamicRegistryProvider extends FabricDynamicRegistryProvider {
        public DynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(HolderLookup.Provider registries, Entries entries) {
            KillYoukaiDamageTypes.bootstrap(createContext(registries, entries));
            KillYoukaiEnchantments.bootstrap(createContext(registries, entries));
        }

        private static <T> BootstrapContext<T> createContext(HolderLookup.Provider registries, Entries entries) {
            return new BootstrapContext<>() {
                @Override
                public Holder.Reference<T> register(ResourceKey<T> resourceKey, T object, Lifecycle lifecycle) {
                    return (Holder.Reference<T>) entries.add(resourceKey, object);
                }

                @Override
                public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> resourceKey) {
                    return registries.lookupOrThrow(resourceKey);
                }
            };
        }

        @Override
        public String getName() {
            return KillYoukaiWithKnives.MOD_NAME + " Dynamic Registries";
        }
    }

    public static class DamageTypeTagProvider extends FabricTagProvider<DamageType> {
        public DamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, Registries.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                    .add(
                            KillYoukaiDamageTypes.COOLDOWN_BYPASSING_MAGIC_KNIVES
                    );
            getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE)
                    .add(
                            KillYoukaiDamageTypes.MAGIC_KNIVES,
                            KillYoukaiDamageTypes.COOLDOWN_BYPASSING_MAGIC_KNIVES
                    );
            getOrCreateTagBuilder(KillYoukaiTags.DamageTypes.KNIVES_BYPASS_COOLDOWN_AFTER)
                    .add(
                            KillYoukaiDamageTypes.MAGIC_KNIVES,
                            KillYoukaiDamageTypes.COOLDOWN_BYPASSING_MAGIC_KNIVES
                    );
        }
    }

    public static class EnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
        public EnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(EnchantmentTags.TOOLTIP_ORDER)
                    .add(
                            KillYoukaiEnchantments.TIMESTASIS,
                            KillYoukaiEnchantments.SCATTER,
                            KillYoukaiEnchantments.SCAVENGE,
                            KillYoukaiEnchantments.TIMECOLLECTION
                    );
            getOrCreateTagBuilder(KillYoukaiTags.Enchantments.MAGIC_KNIVES_EXCLUSIVE)
                    .add(
                            Enchantments.MENDING,
                            Enchantments.UNBREAKING
                    );
            getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE)
                    .add(
                            KillYoukaiEnchantments.SCATTER,
                            KillYoukaiEnchantments.SCAVENGE,
                            KillYoukaiEnchantments.TIMESTASIS
                    );
            getOrCreateTagBuilder(EnchantmentTags.TREASURE)
                    .add(
                            KillYoukaiEnchantments.TIMECOLLECTION
                    );
        }
    }

    public static class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
        public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider wrapperLookup) {
            getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                    .add(
                            reverseLookup(KillYoukaiItems.MAGIC_KNIVES)
                    );
            getOrCreateTagBuilder(KillYoukaiTags.Items.MAGIC_KNIVES_ENCHANTABLE)
                    .add(
                            reverseLookup(KillYoukaiItems.MAGIC_KNIVES)
                    );
            getOrCreateTagBuilder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                    .add(
                            reverseLookup(KillYoukaiItems.MAGIC_KNIVES)
                    );
            getOrCreateTagBuilder(ItemTags.VANISHING_ENCHANTABLE)
                    .add(
                            reverseLookup(KillYoukaiItems.MAGIC_KNIVES)
                    );
        }
    }
}
