package toady.toolbox.mixin.goggles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "hasStatusEffect", at = @At("HEAD"), cancellable = true)
    public void hasStatusEffect(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<Boolean> cir){
        if (effect == StatusEffects.NIGHT_VISION){
            if ((Object)this instanceof PlayerEntity player){
                cir.setReturnValue(!ToadsToolbox.getEquipped(3, player, ModItems.NIGHT_VISION_GOGGLES).isEmpty());
            }
        }
    }
}
