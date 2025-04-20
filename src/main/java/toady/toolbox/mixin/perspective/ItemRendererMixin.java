package toady.toolbox.mixin.perspective;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import toady.toolbox.ToadsToolbox;
import toady.toolbox.item.ModItems;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (stack.getItem() == ModItems.NIGHT_VISION_GOGGLES && renderMode != ModelTransformationMode.HEAD) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ToadsToolbox.id("night_vision_goggles")));
        }
        if (stack.getItem() == ModItems.BANDANA && renderMode != ModelTransformationMode.HEAD) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ToadsToolbox.id("bandana")));
        }
        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() == ModItems.NIGHT_VISION_GOGGLES) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ToadsToolbox.id("night_vision_goggles_head")));
        }
        if (stack.getItem() == ModItems.BANDANA) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(ToadsToolbox.id("bandana_head")));
        }
        return bakedModel;
    }
}