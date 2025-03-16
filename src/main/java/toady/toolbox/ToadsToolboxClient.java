package toady.toolbox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import toady.toolbox.effects.MagicMirrorParticle;
import toady.toolbox.effects.ModParticles;

public class ToadsToolboxClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_MIRROR, MagicMirrorParticle.MirrorFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ENDER_MIRROR, MagicMirrorParticle.MirrorFactory::new);
    }
}
