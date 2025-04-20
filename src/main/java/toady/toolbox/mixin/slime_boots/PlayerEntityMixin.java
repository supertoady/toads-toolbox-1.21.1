package toady.toolbox.mixin.slime_boots;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;
import toady.toolbox.effects.ModSounds;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {
    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        if (!ToadsToolbox.getEquipped(0, (PlayerEntity) (Object) this, ModItems.SLIME_BOOTS).isEmpty()){
            if (fallDistance > 2){
                this.getWorld().playSound(null, this.getBlockPos(), ModSounds.ITEM_SLIME_BOOTS_BOUNCE, SoundCategory.PLAYERS);
                ToadsToolbox.showParticlesToAll(this.getWorld(), new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.getDefaultState()), this.getPos(), 0.1, 10, 0);

                if (!this.isSneaking()){
                    double bounceStrength = Math.log((fallDistance / 7) + 1) / Math.log(1.05) / 16;
                    this.setVelocity(this.getVelocity().x, bounceStrength, this.getVelocity().z);
                    this.velocityModified = true;
                }
            }

            cir.setReturnValue(false);
        }
    }
}
