package net.merchantpug.killyoukaiwithknives.platform;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface KillYoukaiWithKnivesPlatformHelper {

    /**
     * Gets the current platform
     *
     * @return An enum value representing the current platform.
     */
    Platform getPlatform();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    void sendTrackingClientboundPacket(Entity entity, CustomPacketPayload payload);

    boolean previouslyHurtByKnives(Entity entity, Entity directAttacker);

    boolean isTimestasised(Entity entity);

    void setTimestasised(Entity entity, boolean value, @Nullable Vec3 pos);

    @Nullable
    Vec3 getTimestasisPos(Entity entity);
}