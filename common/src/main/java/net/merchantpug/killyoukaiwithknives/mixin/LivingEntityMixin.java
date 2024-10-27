package net.merchantpug.killyoukaiwithknives.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.merchantpug.killyoukaiwithknives.attribute.KillYoukaiAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract AttributeMap getAttributes();

    @Shadow @Nullable
    public abstract AttributeInstance getAttribute(Holder<Attribute> attribute);

    @ModifyReturnValue(method = "getFlyingSpeed", at = @At("RETURN"))
    private float killyoukaiwithknives$modifyAirSpeed(float original) {
        if (getAttributes().hasAttribute(KillYoukaiAttributes.AIR_SPEED)) {
            return original * ((float)getAttribute(KillYoukaiAttributes.AIR_SPEED).getValue() / 0.02F);
        }
        return original;
    }
}
