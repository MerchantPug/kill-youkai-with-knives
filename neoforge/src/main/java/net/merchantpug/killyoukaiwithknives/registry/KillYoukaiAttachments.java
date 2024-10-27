package net.merchantpug.killyoukaiwithknives.registry;

import com.mojang.serialization.Codec;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.UUID;

public class KillYoukaiAttachments {
    public static final AttachmentType<UUID> PREVIOUS_KNIVES_ATTACKER = AttachmentType
            .<UUID>builder(() -> null)
            .serialize(UUIDUtil.CODEC)
            .build();
    public static final AttachmentType<Boolean> IS_TIMESTASISED = AttachmentType
            .builder(() -> false)
            .serialize(Codec.BOOL)
            .build();
    public static final AttachmentType<Vec3> TIMESTASIS_POSITION = AttachmentType
            .builder(() -> Vec3.ZERO)
            .serialize(Vec3.CODEC)
            .build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, KillYoukaiWithKnives.asResource("is_timestasised"), IS_TIMESTASISED);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, KillYoukaiWithKnives.asResource("timestasis_position"), TIMESTASIS_POSITION);
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, KillYoukaiWithKnives.asResource("previous_magic_knives_attacker"), PREVIOUS_KNIVES_ATTACKER);
    }
}
