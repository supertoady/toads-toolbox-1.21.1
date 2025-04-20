package toady.toolbox.mixin.cascade_boots;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;
import toady.toolbox.util.ModEnchantments;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean isInSwimmingPose();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        World world = this.getWorld();
        if ((Object)this instanceof PlayerEntity player) {
            EntityAttributeInstance speedAttribute = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            Identifier id = ToadsToolbox.id("cascade_boots_speed");
            if (speedAttribute != null){
                float value = this.isTouchingWater() ? 1.25f : world.hasRain(this.getBlockPos()) ? 0.75f : 0.3f;

                //check if they are moltenwalker boots
                ItemStack boots = ToadsToolbox.getEquipped(0, player, ModItems.CASCADE_STRIDERS);
                if (ModEnchantments.getLevel(this.getWorld(), ModEnchantments.MOLTENWALKER, boots) > 0){
                    value = this.getWorld().getDimensionEntry().getIdAsString()
                            .equals(DimensionTypes.THE_NETHER_ID.toString()) ? 0.5f : 0.3f;
                }

                EntityAttributeModifier speedModifier = new EntityAttributeModifier(
                        id,
                        value,
                        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                );
                if (!boots.isEmpty()) {
                    if (speedAttribute.getModifier(id) == null){
                        speedAttribute.addTemporaryModifier(speedModifier);
                    }
                    if (speedAttribute.getModifier(id).value() != value){
                        speedAttribute.removeModifier(speedModifier);
                        speedAttribute.addTemporaryModifier(speedModifier);
                    }
                } else {
                    speedAttribute.removeModifier(speedModifier);
                }
            }
        }
    }

    @Inject(method = "canWalkOnFluid", at = @At(value = "HEAD"), cancellable = true)
    private void walkOnFluid(FluidState state, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof PlayerEntity player) {

            Vec3d pos = player.getPos();
            BlockPos bPos = new BlockPos((int) Math.round(pos.x - 0.5f), (int) Math.round(pos.y), (int) Math.round(pos.z - 0.5f));

            boolean canWalkOnFluid = this.getWorld().getFluidState(bPos).isEmpty() && !this.isInSwimmingPose();
            ItemStack boots = ToadsToolbox.getEquipped(0, player, ModItems.CASCADE_STRIDERS);
            boolean hasLavaBoots = ModEnchantments.getLevel(this.getWorld(), ModEnchantments.MOLTENWALKER, boots) > 0;
            if (canWalkOnFluid && !boots.isEmpty() && !player.isSneaking() && (hasLavaBoots ? state.isOf(Fluids.LAVA) : state.isOf(Fluids.WATER))) {
                cir.setReturnValue(true);
            }
        }
    }
}
