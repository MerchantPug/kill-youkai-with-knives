package net.merchantpug.killyoukaiwithknives.sound;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class KillYoukaiSoundEvents {
    public static final Holder<SoundEvent> MAGIC_KNIVES_TIMESTASIS = register("item.magic_knives.timestasis");
    public static final Holder<SoundEvent> MAGIC_KNIVES_TIMERETURN = register("item.magic_knives.timereturn");
    public static final Holder<SoundEvent> MAGIC_KNIVES_HIT = register("item.magic_knives.hit");
    public static final Holder<SoundEvent> MAGIC_KNIVES_THROW = register("item.magic_knives.throw");
    public static final Holder<SoundEvent> ARMOR_EQUIP_MAID = register("item.armor.equip_maid");

    private static Holder<SoundEvent> register(String path) {
        ResourceLocation rl = KillYoukaiWithKnives.asResource(path);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, rl, SoundEvent.createVariableRangeEvent(rl));
    }

    public static void registerAll() {
    }
}
