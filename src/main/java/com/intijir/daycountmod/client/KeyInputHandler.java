package com.intijir.daycountmod.client;

import com.intijir.daycountmod.DayCountMod;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DAY_COUNT = "key.category.day_count_keybind";
    public static final String KEY_DAY_COUNT = "key.daycountmod.day_count";

    public static KeyBinding dayCountKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dayCountKey.isPressed()){
                if (!DayCountMod.keybindPressed){
                    DayCountMod.keybindPressed = true;
                } else {
                    DayCountMod.keybindPressed = false;
                }
            }
        });
    }

    public static void register(){
        dayCountKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_DAY_COUNT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_DAY_COUNT));
        registerKeyInputs();
    }
}
