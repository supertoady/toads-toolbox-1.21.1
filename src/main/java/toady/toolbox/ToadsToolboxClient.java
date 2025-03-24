package toady.toolbox;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import toady.toolbox.effects.MagicMirrorParticle;
import toady.toolbox.effects.ModParticles;
import toady.toolbox.item.ModItems;

public class ToadsToolboxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_MIRROR, MagicMirrorParticle.MirrorFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ENDER_MIRROR, MagicMirrorParticle.MirrorFactory::new);

        TrinketRendererRegistry.registerRenderer(ModItems.NIGHT_VISION_GOGGLES, (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
            if (entity instanceof AbstractClientPlayerEntity player) {
                TrinketRenderer.translateToFace(matrices, (PlayerEntityModel<AbstractClientPlayerEntity>) contextModel, player, headYaw, headPitch);
                matrices.scale(0.63F, 0.63F, 0.63F);
                matrices.translate(0.0F, 0.165F, 0.48F);
                MinecraftClient.getInstance().getItemRenderer()
                        .renderItem(stack, ModelTransformationMode.HEAD, light, OverlayTexture.DEFAULT_UV, matrices,
                                vertexConsumers, entity.getWorld(), 0);
            }
        });
    }
}
