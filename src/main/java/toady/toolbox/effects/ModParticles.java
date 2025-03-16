package toady.toolbox.effects;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import toady.toolbox.ToadsToolbox;

public class ModParticles {
    public static final SimpleParticleType MAGIC_MIRROR = FabricParticleTypes.simple();
    public static final SimpleParticleType ENDER_MIRROR = FabricParticleTypes.simple();

    public static void initialize(){
        Registry.register(Registries.PARTICLE_TYPE, ToadsToolbox.id("magic_mirror"), MAGIC_MIRROR);
        Registry.register(Registries.PARTICLE_TYPE, ToadsToolbox.id("ender_mirror"), ENDER_MIRROR);
    }
}
