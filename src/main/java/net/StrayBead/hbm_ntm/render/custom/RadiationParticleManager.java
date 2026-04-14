package net.StrayBead.hbm_ntm.render.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.StrayBead.hbm_ntm.block.custom.LittleBoyBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod.EventBusSubscriber(modid = "hbm_ntm", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RadiationParticleManager {

    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("hbm_ntm", "textures/particle/radiation_fog.png");
    private static final List<CloudParticleData> PARTICLES = new CopyOnWriteArrayList<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                tick(mc.level);
            }
        }
    }

    public static void tick(net.minecraft.world.level.Level level) {
        synchronized (PARTICLES) {
            for (int i = 0; i < PARTICLES.size(); i++) {
                CloudParticleData data = PARTICLES.get(i);

                if (data == null) continue;

                data.pos = data.pos.add(data.delta);
                data.pos = data.pos.add(data.delta).add(data.velocity);
                data.age++;

                if (data.shouldVelocityDecrease) {
                    data.velocity = data.velocity.scale(0.9d);
                    data.delta = data.delta.scale(0.9d);
                }

//                if (data.shouldAlphaDecrease) {
//                    data.a *= 0.995f;
//                    if (data.a <= 0.01f) {
//                        PARTICLES.remove(data);
//                        continue;
//                    }
//                } else if (data.age >= data.maxAge) {
//                    PARTICLES.remove(data);
//                    continue;
//                }

                if (data.shouldClimbOverLand) {
                    BlockPos bPos = BlockPos.containing(data.pos.x, data.pos.y, data.pos.z);
                    int groundY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, bPos.getX(), bPos.getZ());

                    if (data.yOffset == -1000f) {
                        data.yOffset = (float) (data.pos.y - groundY);
                    }

                    double targetY = groundY + data.yOffset;

                    double smoothY = Mth.lerp(0.2, data.pos.y, targetY);
                    data.pos = new Vec3(data.pos.x, smoothY, data.pos.z);
                }

                float progress = (float) data.age / (float) data.maxAge;
                float maxA = 0.3f;

                if (data.doesAlphaIncrease) {
                    if (progress < 0.2f) {
                        data.a = (progress / 0.2f) * maxA;
                    } else if (progress <= 0.8f) {
                        data.a = maxA;
                    } else {
                        data.a = maxA * (1.0f - ((progress - 0.8f) / 0.2f));
                    }
                } else {
                    if (progress > 0.8f) {
                        data.a = maxA * (1.0f - ((progress - 0.8f) / 0.2f));
                    } else {
                        data.a = maxA;
                    }
                }

                if (data.age >= data.maxAge) {
                    PARTICLES.remove(data);
                }
            }
        }
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, false, false, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, false, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean e, boolean shouldAlphaDecrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, false, shouldAlphaDecrease, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean e, boolean s, boolean h, boolean doesAlphaIncrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, false, false, false, doesAlphaIncrease, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, float vx, float vy, float vz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(vx, vy, vz), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball, boolean e, boolean shouldClimbOverLand) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, shouldClimbOverLand, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball, boolean e, boolean shouldClimbOverLand, boolean v, boolean shouldVelocityDecrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, shouldClimbOverLand, false, shouldVelocityDecrease));
    }

    @SubscribeEvent
    public static void renderSquare(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES || PARTICLES.isEmpty()) return;

        synchronized (PARTICLES) {
            if (PARTICLES.isEmpty()) return;

            Minecraft mc = Minecraft.getInstance();
            PoseStack ps = event.getPoseStack();
            Camera camera = mc.gameRenderer.getMainCamera();
            Vec3 cameraPos = camera.getPosition();

            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
            RenderSystem.setShaderTexture(0, CLOUD_TEXTURE);

            Tesselator tess = Tesselator.getInstance();
            BufferBuilder bf = tess.getBuilder();

            bf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

            int fullBright = 15728880;

            for (int i = 0; i < PARTICLES.size(); i++) {
                CloudParticleData data = PARTICLES.get(i);
                if (data == null || data.pos == null) continue;
                ps.pushPose();

                ps.translate(data.pos.x - cameraPos.x, data.pos.y - cameraPos.y, data.pos.z - cameraPos.z);
                ps.mulPose(camera.rotation());
                ps.mulPose(new Quaternionf().rotationY((float) Math.PI));

                Matrix4f mat = ps.last().pose();
                float s = data.size;

                int r = (int)(data.r * 255);
                int g = (int)(data.g * 255);
                int b = (int)(data.b * 255);
                int a = (int)(data.a * 255);

                bf.vertex(mat, -s, -s, 0).color(r, g, b, a).uv(0f, 1f).uv2(fullBright).endVertex();
                bf.vertex(mat,  s, -s, 0).color(r, g, b, a).uv(1f, 1f).uv2(fullBright).endVertex();
                bf.vertex(mat,  s,  s, 0).color(r, g, b, a).uv(1f, 0f).uv2(fullBright).endVertex();
                bf.vertex(mat, -s,  s, 0).color(r, g, b, a).uv(0f, 0f).uv2(fullBright).endVertex();

                ps.popPose();
            }

            tess.end();
            RenderSystem.depthMask(true);
        }
    }

    public static class CloudParticleData {
        public Vec3 pos;
        public Vec3 delta;
        public Vec3 velocity;
        public float size;
        public final float baseSize;
        public final boolean isShockwave;
        public final boolean shouldAlphaDecrease;
        public final boolean shouldClimbOverLand;
        public final boolean doesAlphaIncrease;
        public final boolean isFireball;
        public final float heatBias;
        public final boolean shouldVelocityDecrease;

        public float r, g, b, a;
        public int age = 0;
        public final int maxAge;
        public final float targetGray;
        public float yOffset;
        public final float lerpSpeed;

        public CloudParticleData(Vec3 pos, Vec3 delta, Vec3 velocity, float size, float r, float g, float b, float a, int maxAge, float lerpSpeed, boolean isShockwave, boolean isFireball, boolean shouldAlphaDecrease, boolean shouldClimbOverLand, boolean doesAlphaIncrease, boolean shouldVelocityDecrease) {
            this.pos = pos;
            this.delta = delta;
            this.velocity = velocity;
            this.yOffset = -1000f;
            this.size = size;
            this.shouldVelocityDecrease = shouldVelocityDecrease;

            this.baseSize = size;
            this.isShockwave = isShockwave;
            this.shouldClimbOverLand = shouldClimbOverLand;
            this.doesAlphaIncrease = doesAlphaIncrease;
            this.shouldAlphaDecrease = shouldAlphaDecrease;
            this.isFireball = isFireball;
            this.heatBias = (float) (Math.random() * 0.6f) - 0.3f;

            this.r = r; this.g = g; this.b = b; this.a = a;
            this.maxAge = maxAge;
            this.lerpSpeed = lerpSpeed;
            this.targetGray = 0.3f + (float)(Math.random() * 0.2f);
        }
    }
}
