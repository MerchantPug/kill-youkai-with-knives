package net.merchantpug.killyoukaiwithknives.registry;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public class KillYoukaiAttachments {
    public static final AttachmentType<UUID> PREVIOUS_KNIVES_ATTACKER = AttachmentRegistry
            .<UUID>builder()
            .persistent(UUIDUtil.CODEC)
            .buildAndRegister(KillYoukaiWithKnives.asResource("previous_magic_knives_attacker"));
}
