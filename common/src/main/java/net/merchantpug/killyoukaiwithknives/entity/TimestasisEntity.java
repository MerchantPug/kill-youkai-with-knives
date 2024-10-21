package net.merchantpug.killyoukaiwithknives.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;

public class TimestasisEntity extends Entity {
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(TimestasisEntity.class, EntityDataSerializers.FLOAT);
    private float increasePerTick = 0.3F;
    private float maxSize = 5.0F;

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
        increasePerTick = tag.getFloat("increase_per_tick");
        maxSize = tag.getFloat("max_size");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("radius", getRadius());
        tag.putFloat("increase_per_tick", increasePerTick);
        tag.putFloat("max_size", maxSize);
    }

    @Override
    public void tick() {
        if (getRadius() < maxSize) {
            setRadius(Math.min(getRadius() + increasePerTick, maxSize));
            setBoundingBox(AABB.ofSize(this.position(), getRadius(), getRadius(), getRadius()));
        }
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