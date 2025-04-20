package toady.toolbox.mixin.bandana;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Inject(at = @At("HEAD"), method = "renderLabelIfPresent", cancellable = true)
    private void dontRenderLabel(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
        if (entity instanceof LivingEntity livingEntity){
            if (entity instanceof PlayerEntity player && !ToadsToolbox.getEquipped(3, player, ModItems.BANDANA).isEmpty()){
                ci.cancel();
            }
            for (ItemStack stack : livingEntity.getArmorItems()){
                if (stack.isOf(ModItems.BANDANA)){
                    ci.cancel();
                }
            }
        }
    }
}
