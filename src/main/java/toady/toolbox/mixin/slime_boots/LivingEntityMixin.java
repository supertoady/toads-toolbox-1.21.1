package toady.toolbox.mixin.slime_boots;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;
import toady.toolbox.util.ModEnchantments;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        World world = this.getWorld();
        if ((Object)this instanceof PlayerEntity player) {
            ItemStack stack = ToadsToolbox.getEquipped(0, player, ModItems.SLIME_BOOTS);
            int level = ModEnchantments.getLevel(world, ModEnchantments.LEAPING, stack);
            if (level > 0){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 5, level-1,true,true));
            }
        }
    }
}
