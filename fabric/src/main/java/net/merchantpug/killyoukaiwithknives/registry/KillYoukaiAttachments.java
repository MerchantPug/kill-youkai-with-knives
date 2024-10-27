package net.merchantpug.killyoukaiwithknives.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class KillYoukaiAttachments {
    public static final AttachmentType<UUID> PREVIOUS_KNIVES_ATTACKER = AttachmentRegistry
            .<UUID>builder()
            .persistent(UUIDUtil.CODEC)
            .buildAndRegister(KillYoukaiWithKnives.asResource("previous_magic_knives_attacker"));

    public static final AttachmentType<Boolean> IS_TIMESTASISED = AttachmentRegistry
            .<Boolean>builder()
            .persistent(Codec.BOOL)
            .buildAndRegister(KillYoukaiWithKnives.asResource("is_timestasised"));
    public static final AttachmentType<Vec3> TIMESTASIS_POSITION = AttachmentRegistry
            .<Vec3>builder()
            .persistent(Vec3.CODEC)
            .buildAndRegister(KillYoukaiWithKnives.asResource("timestasis_position"));
}
