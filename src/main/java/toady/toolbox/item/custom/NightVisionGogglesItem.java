package toady.toolbox.item.custom;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.WearableCarvedPumpkinBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;

import java.util.List;
import java.util.Optional;

public class NightVisionGogglesItem extends TrinketItem implements Equipment {
    public NightVisionGogglesItem(Settings settings) {
        super(settings);
    }

    @Override
    public EquipmentSlot getSlotType() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (ToadsToolbox.hasShiftDown()){
            tooltip.add(Text.literal("§8Enhances your ability to see in the dark."));
        }
        else {
            tooltip.add(Text.literal("§8Press [§7Sneak§8] for Summary"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public static boolean isEquipped(PlayerEntity entity){
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(entity);
        if (entity.getInventory().getArmorStack(3).isOf(ModItems.NIGHT_VISION_GOGGLES)){
            return true;
        }
        return trinketComponent.isPresent() && trinketComponent.get().isEquipped(ModItems.NIGHT_VISION_GOGGLES);
    }
}
