package toady.toolbox.mixin.goggles;

import dev.emi.trinkets.TrinketsMain;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toady.toolbox.item.ModItems;
import toady.toolbox.item.custom.NightVisionGogglesItem;

import java.util.Optional;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow public abstract MinecraftClient getClient();

    @Inject(method = "getNightVisionStrength", at = @At("HEAD"), cancellable = true)
    private static void getNightVisionStrength(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> cir){
        if (entity instanceof PlayerEntity player){
            cir.setReturnValue(NightVisionGogglesItem.isEquipped(player) ? 1.0F : 0.0F);
        }
    }
}
