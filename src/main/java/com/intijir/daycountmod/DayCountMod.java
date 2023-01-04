package com.intijir.daycountmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DayCountMod implements ModInitializer, HudRenderCallback {

    public static float size_x = 2.0F;
    public static float size_y = 2.0F;
    public static int color = 0xffffff;
    public static float loc_x = 2.0F;
    public static float loc_y = 2.0F;
    public static File file = new File("dayCountPrefrences.txt");



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

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] settings = line.split(",");
                size_x = Float.parseFloat(settings[0]);
                size_y = Float.parseFloat(settings[1]);
                color = Integer.parseInt(settings[2]);
                loc_x = Float.parseFloat(settings[3]);
                loc_y = Float.parseFloat(settings[4]);
                if (settings[2].contains("255255255")){
                    color = 0xffffff;
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int currentDay = (int) (MinecraftClient.getInstance().world.getTimeOfDay() / 24000L);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        matrixStack.push();
        matrixStack.translate(loc_x, loc_y, 0);
        matrixStack.scale(size_x, size_y, 2.5f);
        textRenderer.drawWithShadow(matrixStack, "Day: " + currentDay, 2, 2, color);
        matrixStack.pop();

        String content = DayCountMod.size_x + "," + DayCountMod.size_y + "," + DayCountMod.color + "," + DayCountMod.loc_x + "," + DayCountMod.loc_y;
        try {
            FileWriter writer = new FileWriter(DayCountMod.file);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
