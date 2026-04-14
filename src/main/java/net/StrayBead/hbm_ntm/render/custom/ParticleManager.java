package net.StrayBead.hbm_ntm.render.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.StrayBead.hbm_ntm.block.custom.LittleBoyBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import java.util.concurrent.CopyOnWriteArrayList;

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

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "hbm_ntm", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParticleManager {

    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("hbm_ntm", "textures/particle/dust.png");
    private static final List<CloudParticleData> PARTICLES = new CopyOnWriteArrayList<>();

    private static Vec3 clientHeatSource = null;
    private static int heatSourceAge = 0;
    private static final int MAX_HEAT_SOURCE_AGE = 1500;

    public static void setClientHeatSource(Vec3 pos) {
        clientHeatSource = pos;
        heatSourceAge = 0;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                if (clientHeatSource != null) {
                    clientHeatSource = clientHeatSource.add(0, 0.15, 0);
                    heatSourceAge++;

                    if (heatSourceAge > MAX_HEAT_SOURCE_AGE) {
                        clientHeatSource = null;
                    }
                }
                tick(mc.level);
            }
        }
    }

    public static void tick(net.minecraft.world.level.Level level) {
        synchronized (PARTICLES) {
            Vec3 heatSource = clientHeatSource;
            System.out.println(heatSource);
            for (int i = 0; i < PARTICLES.size(); i++) {
                CloudParticleData data = PARTICLES.get(i);

                if (data == null) continue;

                data.pos = data.pos.add(data.delta).add(data.velocity);
                data.age++;

                if (data.shouldVelocityDecrease) {
                    data.velocity = data.velocity.scale(0.9d);
                    data.delta = data.delta.scale(0.9d);
                }

                float colorLerp = data.lerpSpeed;
                data.r += (data.targetGray - data.r) * colorLerp;
                data.g += (data.targetGray - data.g) * colorLerp;
                data.b += (data.targetGray - data.b) * colorLerp;

                if (heatSource != null) {
                    Vec3 visualHeatCenter = heatSource.add(0, -18.0, 0);
                    double distance = data.pos.distanceTo(visualHeatCenter);

                    float yellowRadius = 20.0f;
                    float orangeRadius = 35.0f;
                    float maxHeatDistance = 50.0f;

                    if (distance < maxHeatDistance) {
                        float targetR, targetG, targetB;
                        float heatIntensity;

                        if (distance <= yellowRadius) {
                            targetR = 1.0f;
                            targetG = 1.0f;
                            targetB = 0.0f;
                            heatIntensity = 1.0f;
                        } else if (distance <= orangeRadius) {
                            float t = (float) ((distance - yellowRadius) / (orangeRadius - yellowRadius));
                            targetR = 1.0f;
                            targetG = Mth.lerp(t, 1.0f, 0.45f);
                            targetB = 0.0f;
                            heatIntensity = 1.0f;
                        } else {
                            float t = (float) ((distance - orangeRadius) / (maxHeatDistance - orangeRadius));
                            targetR = 1.0f;
                            targetG = 0.45f;
                            targetB = 0.0f;
                            heatIntensity = 1.0f - t;
                        }

                        targetG = Mth.clamp(targetG + (data.heatBias * 0.2f), 0.0f, 1.0f);

                        data.r = Mth.lerp(heatIntensity, data.r, targetR);
                        data.g = Mth.lerp(heatIntensity, data.g, targetG);
                        data.b = Mth.lerp(heatIntensity, data.b, targetB);

                        data.a = Math.max(data.a, heatIntensity * 0.95f);
                    }
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

                if (data.shouldHaveConvectionCurrent && heatSource != null) {
                    double dx = data.pos.x - heatSource.x;
                    double dz = data.pos.z - heatSource.z;
                    double dy = data.pos.y - heatSource.y;

                    double horizontalDist = Math.sqrt(dx * dx + dz * dz);
                    if (horizontalDist < 0.1) horizontalDist = 0.1;

                    double expansionProgress = (double) data.age / data.maxAge;
                    double vortexRadius = 20.0 + (expansionProgress * 25.0);

                    double vortexStrength = 0.02;
                    if (data.age < 60) {
                        vortexStrength = 0.2;
                    }
                    double diffRadius = horizontalDist - vortexRadius;

                    double rollY = -diffRadius * vortexStrength * 0.55;
                    double rollRadius = dy * vortexStrength;

                    double unitX = dx / horizontalDist;
                    double unitZ = dz / horizontalDist;

                    double expansionSpeed = 0.008;

                    double vx = unitX * (rollRadius + expansionSpeed);
                    double vz = unitZ * (rollRadius + expansionSpeed);
                    double vy = rollY;

                    vx += (Math.random() - 0.5) * 0.01;
                    vz += (Math.random() - 0.5) * 0.01;

                    data.velocity = new Vec3(
                            Mth.lerp(0.15, data.velocity.x, vx),
                            Mth.lerp(0.15, data.velocity.y, vy),
                            Mth.lerp(0.15, data.velocity.z, vz)
                    );
                }

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

                if (data.doesAlphaIncrease) {
                    if (progress < 0.2f) {
                        data.a = (progress / 0.2f) * 0.4f;
                    }
                    else if (progress <= 0.8f) {
                        data.a = 0.4f;
                    }
                    else {
                        data.a = 0.4f * (1.0f - ((progress - 0.8f) / 0.2f));
                    }
                } else {
                    if (progress > 0.8f) {
                        data.a = 1.0f - ((progress - 0.8f) / 0.2f);
                    }
                }

                if (data.isShockwave) {
                    float growthSpeed = 0.1f;
                    float maxAllowedSize = data.baseSize * 4.0f;
                    data.size = Math.min(data.size + growthSpeed, maxAllowedSize);

                    double newVX = data.delta.x * 0.98;
                    double newVZ = data.delta.z * 0.98;
                    double newVY;

                    if (data.isFireball) {
                        newVY = (data.delta.y * 0.90) + 0.015;
                    } else {
                        newVY = data.delta.y * 0.98;
                    }

                    data.delta = new Vec3(newVX, newVY, newVZ);
                }

                float progress2 = (float) data.age / (float) data.maxAge;
                data.r += (data.targetGray - data.r) * data.lerpSpeed;
                data.g += (data.targetGray - data.g) * data.lerpSpeed;
                data.b += (data.targetGray - data.b) * data.lerpSpeed;

                if (progress2 > 0.8f) {
                    data.a = 1.0f - ((progress2 - 0.8f) / 0.2f);
                }

                if (data.age >= data.maxAge) {
                    PARTICLES.remove(data);
                }
            }
        }
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, false, false, false, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, false, false, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean e, boolean shouldAlphaDecrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, false, shouldAlphaDecrease, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean e, boolean s, boolean h, boolean doesAlphaIncrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, false, false, false, false, doesAlphaIncrease, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, float vx, float vy, float vz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(vx, vy, vz), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, false, false, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball, boolean shouldHaveConvectionCurrent, boolean shouldClimbOverLand) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, shouldHaveConvectionCurrent, shouldClimbOverLand, false, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave, boolean isFireball, boolean e, boolean shouldClimbOverLand, boolean v, boolean shouldVelocityDecrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, isShockwave, isFireball, false, false, shouldClimbOverLand, false, shouldVelocityDecrease));
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
            RenderSystem.depthMask(true);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
            RenderSystem.setShaderTexture(0, CLOUD_TEXTURE);

            Tesselator tess = Tesselator.getInstance();
            BufferBuilder bf = tess.getBuilder();

            bf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

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

                bf.vertex(mat, -s, -s, 0).color(r, g, b, a).uv(0f, 1f).endVertex();
                bf.vertex(mat,  s, -s, 0).color(r, g, b, a).uv(1f, 1f).endVertex();
                bf.vertex(mat,  s,  s, 0).color(r, g, b, a).uv(1f, 0f).endVertex();
                bf.vertex(mat, -s,  s, 0).color(r, g, b, a).uv(0f, 0f).endVertex();

                ps.popPose();
            }

            tess.end();
            RenderSystem.depthMask(false);
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
        public final boolean shouldHaveConvectionCurrent;

        public float r, g, b, a;
        public int age = 0;
        public final int maxAge;
        public final float targetGray;
        public float yOffset;
        public final float lerpSpeed;

        public CloudParticleData(Vec3 pos, Vec3 delta, Vec3 velocity, float size, float r, float g, float b, float a, int maxAge, float lerpSpeed, boolean isShockwave, boolean isFireball, boolean shouldAlphaDecrease, boolean shouldHaveConvectionCurrent, boolean shouldClimbOverLand, boolean doesAlphaIncrease, boolean shouldVelocityDecrease) {
            this.pos = pos;
            this.delta = delta;
            this.velocity = velocity;
            this.yOffset = -1000f;
            this.size = size;
            this.shouldVelocityDecrease = shouldVelocityDecrease;
            this.shouldHaveConvectionCurrent = shouldHaveConvectionCurrent;

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
