package com.intijir.daycount;

import com.intijir.daycount.config.DayCountConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayCount implements ModInitializer, HudRenderCallback {
    public static final String MOD_ID = "daycount";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private int cachedDayTime = -1;


    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Day Count Mod");
        DayCountConfig.init();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                int currentDayTime = (int) (MinecraftClient.getInstance().world.getTimeOfDay() / 24000L);
                if (currentDayTime != cachedDayTime) {
                    // Day time has changed, update the cached value and do any necessary updates
                    cachedDayTime = currentDayTime;
                    // Update the GUI or other elements as needed
                    MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
                    HudRenderCallback.EVENT.register(this);
                }
            }
        });
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (DayCountConfig.INSTANCE.dayCountEnabled) {
            int currentDay = (int) (MinecraftClient.getInstance().world.getTimeOfDay() / 24000L);
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            MatrixStack matrixStack = drawContext.getMatrices();

            matrixStack.push();
            matrixStack.translate(DayCountConfig.INSTANCE.locationX, DayCountConfig.INSTANCE.locationY, 0);
            matrixStack.scale(DayCountConfig.INSTANCE.sizeX, DayCountConfig.INSTANCE.sizeY, 2.5f);
            drawContext.drawTextWithShadow(textRenderer, "Day: " + (currentDay + DayCountConfig.INSTANCE.dayOffset), 2, 2, DayCountConfig.INSTANCE.color);
            matrixStack.pop();
        }
    }
}
