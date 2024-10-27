package net.merchantpug.killyoukaiwithknives.network.clientbound;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public record SyncTimestasisStateClientboundPacket(int entityId, boolean value) implements CustomPacketPayload {
    public static final ResourceLocation ID = KillYoukaiWithKnives.asResource("sync_timestasis_state");
    public static final Type<SyncTimestasisStateClientboundPacket> TYPE = new Type<>(ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncTimestasisStateClientboundPacket> STREAM_CODEC = StreamCodec.of(SyncTimestasisStateClientboundPacket::write, SyncTimestasisStateClientboundPacket::new);

    public SyncTimestasisStateClientboundPacket(RegistryFriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean());
    }

    public static void write(RegistryFriendlyByteBuf buf, SyncTimestasisStateClientboundPacket packet) {
        buf.writeInt(packet.entityId);
        buf.writeBoolean(packet.value);
    }

    public void handle() {
        Minecraft.getInstance().execute(() -> {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity == null)
                return;
            KillYoukaiWithKnives.getHelper().setTimestasised(entity, value);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
