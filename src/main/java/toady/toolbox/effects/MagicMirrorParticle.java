package toady.toolbox.effects;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import toady.toolbox.ToadsToolbox;

@Environment(EnvType.CLIENT)
public class MagicMirrorParticle extends SpriteBillboardParticle {
    public MagicMirrorParticle(ClientWorld clientWorld, double x, double y, double z,
                               double xv, double yv, double zv, float gravity) {
        super(clientWorld, x, y, z, xv, yv, zv);
        this.maxAge = 100;
        this.gravityStrength = gravity;
        this.velocityMultiplier = 0.6F;
        this.scale *= 0.75F;
        this.velocityX = xv;
        this.velocityY = yv;
        this.velocityZ = zv;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        float f = 1f - ((float) this.age / this.maxAge);
        this.setAlpha(f);
        super.tick();
    }

    @Override
    public float getSize(float tickDelta) {
        float f = 1 - ((this.age + tickDelta) / this.maxAge);
        return (float) (this.scale * (1 - Math.cos((f * Math.PI) / 2)));
    }

    @Environment(EnvType.CLIENT)
    public static class MirrorFactory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public MirrorFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            MagicMirrorParticle magicMirrorParticle = new MagicMirrorParticle(world, x, y, z, velocityX, velocityY, velocityZ, 0.3F);
            magicMirrorParticle.setSprite(spriteProvider);
            return magicMirrorParticle;
        }
    }
}
