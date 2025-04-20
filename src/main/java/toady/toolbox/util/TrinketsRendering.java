package toady.toolbox.util;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import org.joml.Quaternionf;
import toady.toolbox.item.ModItems;

public class TrinketsRendering {
    public static void renderTrinkets(){
        TrinketRendererRegistry.registerRenderer(ModItems.NIGHT_VISION_GOGGLES, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            if (contextModel instanceof BipedEntityModel<? extends LivingEntity> biped) {
                biped.head.rotate(matrices);
                matrices.scale(0.63F, 0.63F, 0.63F);
                matrices.translate(0.0F, -0.4F, 0.0F);
                matrices.multiply(new Quaternionf().rotationY((float)Math.toRadians(180)));
                matrices.multiply(new Quaternionf().rotationX((float)Math.toRadians(180)));
                MinecraftClient.getInstance().getItemRenderer()
                        .renderItem(stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices,
                                vertexConsumers, entity.getWorld(), 0);
            }
        });

        TrinketRendererRegistry.registerRenderer(ModItems.BANDANA, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            if (contextModel instanceof BipedEntityModel<? extends LivingEntity> biped) {
                biped.head.rotate(matrices);
                matrices.scale(0.63F, 0.63F, 0.63F);
                matrices.translate(0.0F, -0.4f, 0.0f);
                matrices.multiply(new Quaternionf().rotationY((float)Math.toRadians(180)));
                matrices.multiply(new Quaternionf().rotationX((float)Math.toRadians(180)));
                MinecraftClient.getInstance().getItemRenderer()
                        .renderItem(stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices,
                                vertexConsumers, entity.getWorld(), 0);
            }
        });
    }
}
