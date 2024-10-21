package net.merchantpug.killyoukaiwithknives.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiAttachments;
import net.merchantpug.killyoukaiwithknives.registry.KillYoukaiTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "hurt", at = @At("RETURN"))
    private boolean killyoukaiwithknives$setKnivesAttackerAttachment(boolean original, DamageSource source, float amount) {
        if (original) {
            if (source.is(KillYoukaiTags.DamageTypes.KNIVES_BYPASS_COOLDOWN_AFTER) && source.getEntity() != null)
                setAttached(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER, source.getEntity().getUUID());
            else
                removeAttached(KillYoukaiAttachments.PREVIOUS_KNIVES_ATTACKER);
        }
        return original;
    }
}
