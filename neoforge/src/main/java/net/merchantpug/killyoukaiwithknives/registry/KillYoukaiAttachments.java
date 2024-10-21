package net.merchantpug.killyoukaiwithknives.registry;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.merchantpug.killyoukaiwithknives.registry.internal.RegistrationCallback;
import net.minecraft.core.UUIDUtil;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.UUID;

public class KillYoukaiAttachments {
    public static final AttachmentType<UUID> PREVIOUS_KNIVES_ATTACKER = AttachmentType
            .<UUID>builder(() -> null)
            .serialize(UUIDUtil.CODEC)
            .build();

    public static void registerAll(RegistrationCallback<AttachmentType<?>> callback) {
        callback.register(NeoForgeRegistries.ATTACHMENT_TYPES, KillYoukaiWithKnives.asResource("previous_magic_knives_attacker"), PREVIOUS_KNIVES_ATTACKER);
    }
}
