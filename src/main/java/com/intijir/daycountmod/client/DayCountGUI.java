package com.intijir.daycountmod.client;

import com.intijir.daycountmod.DayCountMod;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import java.io.FileWriter;

import static com.intijir.daycountmod.DayCountMod.*;

public class DayCountGUI extends LightweightGuiDescription {
    public DayCountGUI(MinecraftClient client) {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(60, 60);
        root.setInsets(Insets.ROOT_PANEL);

        WTextField sizexTextField = new WTextField(Text.of("   Width"));

        root.add(sizexTextField, 0,0,3,1);

        sizexTextField.setChangedListener(text -> {
            if (sizexTextField.getText().trim() == ""){
                System.out.println("X is empty");
            } else {
                try {
                    DayCountMod.size_x = Float.parseFloat(sizexTextField.getText());
                    saveToFile();
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        WTextField sizeyTextField = new WTextField(Text.of("  Height"));

        root.add(sizeyTextField, 4,0,3,1);

        sizeyTextField.setChangedListener(text -> {
            if (sizeyTextField.getText().trim() == ""){
                System.out.println("Y is empty");
            } else {
                try {
                    DayCountMod.size_y = Float.parseFloat(sizeyTextField.getText());
                    saveToFile();
                } catch (Exception e){
                    System.out.println(e);
                }

            }
        });

        WTextField colorTextField = new WTextField(Text.of("Color RGB"));

        root.add(colorTextField, 2,2,3,1);

        colorTextField.setChangedListener(text -> {
            if (colorTextField.getText().trim() == ""){
                System.out.println("Color is empty");
            } else {
                try {
                    if (colorTextField.getText().contains("255255255")){
                        DayCountMod.color = 0xffffff;
                    } else {
                        DayCountMod.color = Integer.parseInt(colorTextField.getText());
                    }
                    saveToFile();
                } catch (Exception e){
                    System.out.println(e);
                }

            }
        });

        WTextField locxTextField = new WTextField(Text.of("  Loc X"));

        root.add(locxTextField, 0,4,3,1);

        locxTextField.setChangedListener(text -> {
            if (locxTextField.getText().trim() == ""){
            } else {
                try {
                    DayCountMod.loc_x = Float.parseFloat(locxTextField.getText());
                    saveToFile();
                } catch (Exception e){
                    System.out.println(e);
                }

            }
        });

        WTextField locyTextField = new WTextField(Text.of("  Loc Y"));

        root.add(locyTextField, 4,4,3,1);

        locyTextField.setChangedListener(text -> {
            if (locyTextField.getText().trim() == ""){
            } else {
                try {
                    DayCountMod.loc_y = Float.parseFloat(locyTextField.getText());
                    saveToFile();
                } catch (Exception e){
                    System.out.println(e);
                }

            }
        });

        root.validate(this);
    }

    public void saveToFile(){
        String content = size_x + "," + DayCountMod.size_y + "," + DayCountMod.color + "," + loc_x + "," + loc_y;
        try {
            FileWriter writer = new FileWriter(DayCountMod.file);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

