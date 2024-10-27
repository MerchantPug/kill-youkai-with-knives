package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.attribute.KillYoukaiAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "getFlyingSpeed", at = @At("RETURN"))
    private float killyoukaiwithknives$modifyAirSpeed(float original) {
        if (getAttributes().hasAttribute(KillYoukaiAttributes.AIR_SPEED)) {
            return original * ((float)getAttribute(KillYoukaiAttributes.AIR_SPEED).getValue() / 0.02F);
        }
        return original;
    }
}
