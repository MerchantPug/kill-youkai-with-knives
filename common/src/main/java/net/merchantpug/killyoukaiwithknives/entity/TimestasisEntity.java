package net.merchantpug.killyoukaiwithknives.entity;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TimestasisEntity extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(TimestasisEntity.class, EntityDataSerializers.FLOAT);
    private float increasePerTick = 0.1F;
    private float maxSize = 8.0F;
    private long lifespan = 80;
    @Nullable
    private UUID ownerUUID;
    @Nullable
    private Entity cachedOwner;
    private List<Entity> affectedEntities = new ArrayList<>();

    public static final Map<Holder<Attribute>, AttributeModifier> ATTRIBUTE_MAP = Map.of(
            Attributes.MOVEMENT_SPEED, new AttributeModifier(KillYoukaiWithKnives.asResource("timestasis.movement_speed"), -0.6, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
            Attributes.ATTACK_SPEED, new AttributeModifier(KillYoukaiWithKnives.asResource("timestasis.attack_speed"), -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

    public TimestasisEntity(EntityType<TimestasisEntity> entityType, Level level) {
        super(entityType, level);
        noPhysics = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(RADIUS, 0.0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        setRadius(tag.getFloat("radius"));
        if (tag.contains("increase_per_tick"))
            increasePerTick = tag.getFloat("increase_per_tick");
        if (tag.contains("max_size"))
            maxSize = tag.getFloat("max_size");
        if (tag.contains("owner"))
            ownerUUID = tag.getUUID("owner");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("radius", getRadius());
        tag.putFloat("increase_per_tick", increasePerTick);
        tag.putFloat("max_size", maxSize);
        if (ownerUUID != null)
            tag.putUUID("owner", ownerUUID);
    }

    @Override
    public void tick() {
        if (level().isClientSide)
            return;
        if (tickCount > lifespan) {
            affectedEntities.forEach(this::removeEntityEffects);
            discard();
            return;
        }

        List<Entity> currentlyAffected = level().getEntitiesOfClass(Entity.class, getBoundingBox()).stream().filter(living -> getOwner() == null || !living.is(getOwner())).toList();

        affectedEntities.stream().filter(living -> !currentlyAffected.contains(living)).forEach(this::removeEntityEffects);
        affectedEntities = currentlyAffected;
        affectedEntities.forEach(this::modifyEntities);


        if (getRadius() < maxSize) {
            setRadius(Math.min(getRadius() + increasePerTick, maxSize));
            setBoundingBox(AABB.ofSize(this.position(), getRadius(), getRadius(), getRadius()));
        }
    }

    private void modifyEntities(Entity entity) {
        if (entity instanceof LivingEntity living) {
            ATTRIBUTE_MAP.forEach((attributeHolder, attributeModifier) -> {
                if (!living.getAttributes().hasAttribute(attributeHolder))
                    return;
                living.getAttribute(attributeHolder).addOrUpdateTransientModifier(attributeModifier);
            });
        }
    }

    private void removeEntityEffects(Entity entity) {
        if (entity instanceof Projectile) {

        }
        if (entity instanceof LivingEntity living) {
            ATTRIBUTE_MAP.forEach((attributeHolder, attributeModifier) -> {
                if (!living.getAttributes().hasAttribute(attributeHolder))
                    return;
                living.getAttribute(attributeHolder).removeModifier(attributeModifier);
            });
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (cachedOwner != null && !cachedOwner.isRemoved()) {
            return this.cachedOwner;
        } else if (ownerUUID != null && level() instanceof ServerLevel serverlevel) {
            cachedOwner = serverlevel.getEntity(ownerUUID);
            return cachedOwner;
        } else {
            return null;
        }
    }

    public void setOwner(@Nullable Entity entity) {
        if (entity != null)
            ownerUUID = entity.getUUID();
        else
            ownerUUID = null;
        cachedOwner = entity;
    }

    public float getRadius() {
        return entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        entityData.set(RADIUS, radius);
    }

    public void setIncreasePerTick(float increasePerTick) {
        this.increasePerTick = increasePerTick;
    }

    public void setMaxSize(float maxSize) {
        this.maxSize = maxSize;
    }

    public void setLifespan(long lifespan) {
        this.lifespan = lifespan;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return false;
    }

    @Override
    protected boolean couldAcceptPassenger() {
        return false;
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }
}