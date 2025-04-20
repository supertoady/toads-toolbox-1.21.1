package toady.toolbox.mixin.cascade_boots;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;
import toady.toolbox.util.ModEnchantments;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
    private static void renderFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci){
        ItemStack stack = ToadsToolbox.getEquipped(0, client.player, ModItems.CASCADE_STRIDERS);
        assert client.world != null;
        if (ModEnchantments.getLevel(client.world, ModEnchantments.MOLTENWALKER, stack) > 0) ci.cancel();
    }
}
