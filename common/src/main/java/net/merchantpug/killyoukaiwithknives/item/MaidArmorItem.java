package net.merchantpug.killyoukaiwithknives.item;

import net.merchantpug.killyoukaiwithknives.KillYoukaiWithKnives;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MaidArmorItem extends ArmorItem {
    public MaidArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Nullable
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        switch (slot) {
            case HEAD, CHEST: {
                return KillYoukaiWithKnives.asResource("textures/models/armor/maid_layer_1.png");
            }
            case LEGS: {
                return KillYoukaiWithKnives.asResource("textures/models/armor/maid_layer_2.png");
            }
            case FEET: {
                return KillYoukaiWithKnives.asResource("textures/models/armor/maid_layer_3.png");
            }
            default: {
                break;
            }
        }
        return null;
    }
}
