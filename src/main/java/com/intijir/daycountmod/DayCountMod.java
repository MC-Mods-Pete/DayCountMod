package com.intijir.daycountmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.concurrent.atomic.AtomicInteger;

public class DayCountMod implements ModInitializer, HudRenderCallback {

    public static Boolean keybindPressed = false;

    @Override
    public void onInitialize() {
        AtomicInteger tickNumber = new AtomicInteger();
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (tickNumber.get() == 100) {
                MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
                HudRenderCallback.EVENT.register(this);

                tickNumber.set(0);
            } else {
                tickNumber.getAndIncrement();
            }

        });
    }


    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        if (keybindPressed) {
            int currentDay = (int) (MinecraftClient.getInstance().world.getTimeOfDay() / 24000L);
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            matrixStack.push();
            matrixStack.translate(2, 2, 0);
            matrixStack.scale(2.5f, 2.5f, 2.5f);
            textRenderer.drawWithShadow(matrixStack, "Day: " + currentDay, 2, 2, 0xffffff);
            matrixStack.pop();
        }
        else {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            matrixStack.push();
            textRenderer.drawWithShadow(matrixStack, "" , 2, 2, 0xffffff);
            matrixStack.pop();
        }

    }
}
