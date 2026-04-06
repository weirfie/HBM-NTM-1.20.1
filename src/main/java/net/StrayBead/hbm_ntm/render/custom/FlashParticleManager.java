package net.StrayBead.hbm_ntm.render.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
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
public class FlashParticleManager {

    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("hbm_ntm", "textures/particle/white_flash.png");
    private static final List<CloudParticleData> PARTICLES = new CopyOnWriteArrayList<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tick();
        }
    }

    public static void tick() {
        synchronized (PARTICLES) {
            for (int i = 0; i < PARTICLES.size(); i++) {
                CloudParticleData data = PARTICLES.get(i);

                if (data == null) continue;

                data.pos = data.pos.add(data.delta);
                data.age++;

                data.size += 50;

                if (data.isShockwave) {
                    float growthSpeed = 0.1f;
                    float maxAllowedSize = data.baseSize * 6.0f;
                    data.size = Math.min(data.size + growthSpeed, maxAllowedSize);
                    data.delta = data.delta.scale(0.99);
                }

                float progress = (float) data.age / (float) data.maxAge;
                data.r += (data.targetGray - data.r) * data.lerpSpeed;
                data.g += (data.targetGray - data.g) * data.lerpSpeed;
                data.b += (data.targetGray - data.b) * data.lerpSpeed;

                if (progress > 0.8f) {
                    data.a = 1.0f - ((progress - 0.8f) / 0.2f);
                }

                if (data.age >= data.maxAge) {
                    PARTICLES.remove(data);
                }
            }
        }
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), size, r, g, b, a, maxAge, transitionSpeed, false));
    }

    public static void addParticle(float x, float y, float z, float size, float r, float g, float b, float a, float dx, float dy, float dz, int maxAge, float transitionSpeed, boolean isShockwave) {
        PARTICLES.add(new CloudParticleData(new Vec3(x, y, z), new Vec3(dx, dy, dz), size, r, g, b, a, maxAge, transitionSpeed, isShockwave));
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
        public float size;
        public final float baseSize;
        public final boolean isShockwave;

        public float r, g, b, a;
        public int age = 0;
        public final int maxAge;
        public final float targetGray;
        public final float lerpSpeed;

        public CloudParticleData(Vec3 pos, Vec3 delta, float size, float r, float g, float b, float a, int maxAge, float lerpSpeed, boolean isShockwave) {
            this.pos = pos;
            this.delta = delta;
            this.size = size;

            this.baseSize = size;
            this.isShockwave = isShockwave;

            this.r = r; this.g = g; this.b = b; this.a = a;
            this.maxAge = maxAge;
            this.lerpSpeed = lerpSpeed;
            this.targetGray = 0.3f + (float)(Math.random() * 0.2f);
        }
    }
}
