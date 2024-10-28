package net.merchantpug.killyoukaiwithknives.datagen;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.merchantpug.killyoukaiwithknives.KillYoukaiTags;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.damage.KillYoukaiDamageTypes;
import net.merchantpug.killyoukaiwithknives.enchantment.KillYoukaiEnchantments;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiItems;
import net.merchantpug.killyoukaiwithknives.item.KillYoukaiLootTables;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class KillYoukaiWithKnivesDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(DynamicRegistryProvider::new);
        pack.addProvider(DamageTypeTagProvider::new);
        pack.addProvider(EnchantmentTagProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(ChestLootTableProvider::new);
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
            getOrCreateTagBuilder(KillYoukaiTags.Enchantments.IN_KITCHEN)
                    .add(
                            KillYoukaiEnchantments.SCATTER,
                            KillYoukaiEnchantments.SCAVENGE,
                            KillYoukaiEnchantments.TIMECOLLECTION,
                            KillYoukaiEnchantments.TIMESTASIS,
                            Enchantments.SHARPNESS
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
            getOrCreateTagBuilder(KillYoukaiTags.Items.MAGIC_KNIVES_REPAIR_INGREDIENT)
                    .add(
                            reverseLookup(Items.IRON_INGOT)
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

    public static class ChestLootTableProvider extends SimpleFabricLootTableProvider {
        private final CompletableFuture<HolderLookup.Provider> registries;

        public ChestLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup, LootContextParamSets.CHEST);
            registries = lookup;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
            HolderLookup.Provider lookup = registries.join();
            Holder<Enchantment> timecollection = lookup.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(KillYoukaiEnchantments.TIMECOLLECTION);
            HolderSet<Enchantment> inKitchenEnchantments = lookup.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(KillYoukaiTags.Enchantments.IN_KITCHEN);

            biConsumer.accept(KillYoukaiLootTables.WOODLAND_MANSION_KITCHEN, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(Items.BOOK)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(lookup, UniformGenerator.between(25.0F, 30.0F)).fromOptions(inKitchenEnchantments))
                                    .apply(new SetEnchantmentsFunction.Builder(true).withEnchantment(timecollection, ConstantValue.exactly(1.0F))))
                    ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 2.0F)
                            ).add(LootItem.lootTableItem(Items.BOOK)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(lookup, UniformGenerator.between(25.0F, 30.0F)).fromOptions(inKitchenEnchantments))
                            )
                    ).withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 6.0F)
                            ).add(LootItem.lootTableItem(Items.BREAD)
                                    .setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 12.0F)))
                            ).add(LootItem.lootTableItem(Items.BAKED_POTATO)
                                    .setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 12.0F)))
                            ).add(LootItem.lootTableItem(Items.PUMPKIN_PIE)
                                    .setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 12.0F)))
                            ).add(LootItem.lootTableItem(Items.GLISTERING_MELON_SLICE)
                                    .setWeight(2)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                            ).add(LootItem.lootTableItem(Items.BEETROOT_SOUP)
                                    .setWeight(2)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                            ).add(LootItem.lootTableItem(Items.GOLDEN_CARROT)
                                    .setWeight(2)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 18.0F)))
                            ).add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)
                                    .setWeight(1)
                            )
                    ).withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(Items.RABBIT_STEW))
                    )
            );
        }
    }
}
