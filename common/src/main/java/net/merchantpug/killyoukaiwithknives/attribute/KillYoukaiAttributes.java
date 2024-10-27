package net.merchantpug.killyoukaiwithknives.attribute;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class KillYoukaiAttributes {
    public static final Holder<Attribute> AIR_SPEED = Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, KillYoukaiWithKnives.asResource("generic.air_speed"), new RangedAttribute("attribute.name.killyoukaiwithknives.generic.air_speed", 0.02F, -1024.0F, 1024.0F).setSyncable(true));

    public static void registerAll() {
    }
}
