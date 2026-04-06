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
public class ExplosionParticleManager {

    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("hbm_ntm", "textures/particle/dust.png");
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
        for (CloudParticleData data : PARTICLES) {
            data.pos = data.pos.add(data.delta).add(data.velocity);
            data.age++;

            if (data.shouldVelocityDecrease) {
                data.velocity = data.velocity.scale(0.9d);
                data.delta = data.delta.scale(0.9d);
            }

            if (data.lerpSpeed == 0.01f) {
                data.delta = data.delta.add(new Vec3(0.01, 0, 0));
            }

            float progress = (float) data.age / (float) data.maxAge;
            float var = data.colorVariation;

            if (data.lerpSpeed > 0.0f) {

                if (progress < data.transition1 - data.lerpSpeed) {
                    float subProgress = progress / data.transition1;
                    data.r = 1.0f + var;
                    data.g = Mth.lerp(subProgress, 1.0f + var, 0.5f + var);
                    data.b = Mth.lerp(subProgress, 0.2f, 0.0f);
                }
                else if (progress < data.transition2 - data.lerpSpeed) {
                    float subProgress = (progress - data.transition1) / (data.transition2 - data.transition1);
                    data.r = 1.0f + var;
                    data.g = Mth.lerp(subProgress, 0.5f + var, 0.15f + var);
                    data.b = 0.0f;
                }
                else if (progress < data.transition3 - data.lerpSpeed) {
                    float subProgress = (progress - data.transition2) / (data.transition3 - data.transition2);
                    data.r = Mth.lerp(subProgress, 1.0f + var, data.targetGray);
                    data.g = Mth.lerp(subProgress, 0.15f + var, data.targetGray);
                    data.b = Mth.lerp(subProgress, 0.0f, data.targetGray);
                }
                else {
                    data.r = data.targetGray;
                    data.g = data.targetGray;
                    data.b = data.targetGray;
                }
            }

            data.r = Mth.clamp(data.r, 0.0f, 1.0f);
            data.g = Mth.clamp(data.g, 0.0f, 1.0f);
            data.b = Mth.clamp(data.b, 0.0f, 1.0f);

            if (progress > 0.8f) {
                data.a = 1.0f - ((progress - 0.8f) / 0.2f);
            }

            if (data.age >= data.maxAge) {
                PARTICLES.remove(data);
            }
        }
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, false, 0.3f));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean shouldVelocityDecrease) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, shouldVelocityDecrease, 0.3f));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean shouldVelocityDecrease, float targetGray) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), new Vec3(0, 0, 0), size, r, g, b, a, maxAge, transitionSpeed, shouldVelocityDecrease, targetGray));
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
        public final boolean shouldVelocityDecrease;

        public float r, g, b, a;
        public int age = 0;
        public final int maxAge;
        public final float targetGray;
        public float yOffset;
        public final float lerpSpeed;
        public final float colorVariation;
        public final float transition1;
        public final float transition2;
        public final float transition3;

        public CloudParticleData(Vec3 pos, Vec3 delta, Vec3 velocity, float size, float r, float g, float b, float a, int maxAge, float lerpSpeed, boolean shouldVelocityDecrease, float targetGray) {
            this.pos = pos;
            this.delta = delta;
            this.velocity = velocity;
            this.yOffset = -1000f;
            this.size = size;
            this.colorVariation = (float)(Math.random() * 0.2f) - 0.1f;
            this.transition1 = 0.02f + (float)Math.random() * 0.06f;
            this.transition2 = this.transition1 + 0.05f + (float)Math.random() * 0.10f;
            this.transition3 = this.transition2 + 0.20f + (float)Math.random() * 0.3f;

            this.baseSize = size;
            this.shouldVelocityDecrease = shouldVelocityDecrease;

            this.r = r; this.g = g; this.b = b; this.a = a;
            this.maxAge = maxAge;
            this.lerpSpeed = lerpSpeed;
            this.targetGray = targetGray + (float)(Math.random() * 0.2f);
        }
    }
}
