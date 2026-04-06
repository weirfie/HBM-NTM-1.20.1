package net.StrayBead.hbm_ntm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ShockwaveParticle extends TextureSheetParticle {

    private float gravitytick = -5f;
    private int delayTicks = 2;
    private int timeSinceSpawn = 0;

    public static ShockwaveParticleProvider provider(SpriteSet spriteSet) {
        return new ShockwaveParticleProvider(spriteSet);
    }

    public static class ShockwaveParticleProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public ShockwaveParticleProvider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ShockwaveParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }

    private final SpriteSet spriteSet;

    protected ShockwaveParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
        super(world, x, y, z);
        this.spriteSet = spriteSet;
        this.setSize(0.2f, 0.2f);
        this.quadSize *= 40f;
        this.lifetime = (int) Math.max(1, 300 + (this.random.nextInt(1000) - 500));
        this.gravity = gravitytick;
        this.hasPhysics = false;
        this.xd = vx * 1;
        this.yd = vy * 1;
        this.zd = vz * 1;
        this.pickSprite(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();

        timeSinceSpawn++;

        if (timeSinceSpawn >= delayTicks) {
            gravity = 0f;
        }
    }
}
