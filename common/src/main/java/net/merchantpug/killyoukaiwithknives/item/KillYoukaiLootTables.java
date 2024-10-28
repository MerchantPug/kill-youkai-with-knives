package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class KillYoukaiLootTables {
    public static final ResourceKey<LootTable> WOODLAND_MANSION_KITCHEN = ResourceKey.create(Registries.LOOT_TABLE, KillYoukaiWithKnives.asResource("chests/woodland_mansion_kitchen"));
}
