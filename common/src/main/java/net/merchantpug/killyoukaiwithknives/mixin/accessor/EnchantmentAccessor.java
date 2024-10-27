package net.merchantpug.killyoukaiwithknives.mixin.accessor;

import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.function.Consumer;

@Mixin(Enchantment.class)
public interface EnchantmentAccessor {
    @Invoker("applyEffects")
    static <T> void killyoukaiwithknives$invokeApplyEffects(List<ConditionalEffect<T>> effects, LootContext context, Consumer<T> applier) {
        throw new RuntimeException("");
    }
}
