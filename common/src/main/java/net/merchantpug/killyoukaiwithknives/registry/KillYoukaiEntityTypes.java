package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.entity.MagicKnifeEntity;
import net.merchantpug.killyoukaiwithknives.entity.TimestasisEntity;
import net.merchantpug.killyoukaiwithknives.platform.Platform;
import net.merchantpug.killyoukaiwithknives.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class KillYoukaiEntityTypes {
    public static final EntityType<MagicKnifeEntity> MAGIC_KNIFE = buildEntityType(EntityType.Builder.<MagicKnifeEntity>of(MagicKnifeEntity::new, MobCategory.MISC)
            .sized(0.4F, 0.4F)
            .clientTrackingRange(4), "killyoukaiwithknives:magic_knife");
    public static final EntityType<TimestasisEntity> TIMESTASIS = buildEntityType(EntityType.Builder.of(TimestasisEntity::new, MobCategory.MISC)
            .fireImmune()
            .sized(0.0F, 0.0F)
            .clientTrackingRange(10), "killyoukaiwithknives:timestasis");

    public static void registerAll(RegistrationCallback<EntityType<?>> callback) {
        callback.register(BuiltInRegistries.ENTITY_TYPE, KillYoukaiWithKnives.asResource("magic_knife"), MAGIC_KNIFE);
        callback.register(BuiltInRegistries.ENTITY_TYPE, KillYoukaiWithKnives.asResource("timestasis"), TIMESTASIS);
    }

    public static <T extends Entity> EntityType<T> buildEntityType(EntityType.Builder<T> builder, String neoForgeDfuType) {
       return builder.build(KillYoukaiWithKnives.getHelper().getPlatform() == Platform.FABRIC ? null : neoForgeDfuType);
    }
}
