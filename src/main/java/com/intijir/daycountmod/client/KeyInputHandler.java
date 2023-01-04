package com.intijir.daycountmod.client;

import com.intijir.daycountmod.DayCountMod;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DAY_COUNT = "key.category.day_count_keybind";
    public static final String KEY_DAY_COUNT = "key.daycountmod.day_count";
    public static final String KEY_DAY_COUNT_GUI = "key.daycountmod.day_count_gui";

    public static KeyBinding dayCountKey;
    public static KeyBinding dayCountGUIKey;
    private static int color = 0xffffff;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dayCountKey.isPressed()){
                if (DayCountMod.color != 255255255) {
                    color = DayCountMod.color;
                    DayCountMod.color = 255255255;
                } else {
                    DayCountMod.color = color;
                }
            }
            if (dayCountGUIKey.isPressed()){
                client.getInstance().setScreen(new CottonClientScreen(new DayCountGUI(client)));
            }
        });
    }

    public static void register(){
        dayCountKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_DAY_COUNT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_DAY_COUNT));
        dayCountGUIKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_DAY_COUNT_GUI, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_DAY_COUNT));
        registerKeyInputs();
    }
}
