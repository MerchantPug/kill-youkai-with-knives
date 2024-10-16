package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.entity.Timestop;
import net.merchantpug.killyoukaiwithknives.platform.Platform;
import net.merchantpug.killyoukaiwithknives.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class KillYoukaiEntityTypes {
    public static final EntityType<Timestop> TIMESTOP = buildEntityType(EntityType.Builder.of(Timestop::new, MobCategory.MISC)
            .fireImmune()
            .sized(0.0F, 0.0F)
            .clientTrackingRange(10), "killyoukaiwithknives:timestop");

    public static void registerAll(RegistrationCallback<EntityType<?>> callback) {
        callback.register(BuiltInRegistries.ENTITY_TYPE, KillYoukaiWithKnives.asResource("timestop"), TIMESTOP);
    }

    public static <T extends Entity> EntityType<T> buildEntityType(EntityType.Builder<T> builder, String neoForgeDfuType) {
       return builder.build(KillYoukaiWithKnives.getHelper().getPlatform() == Platform.FABRIC ? null : neoForgeDfuType);
    }
}
