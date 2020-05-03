package ch.dams333.damsLib.adaptaters;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FireworkAdaptater {

    public static void serializeAdapt(ConfigurationSection section, FireworkMeta fireworkMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        sec.set("Power", fireworkMeta.getPower());
        ConfigurationSection eff = sec.createSection("Effects");
        List<FireworkEffect> effects = fireworkMeta.getEffects();

        int index = 1;

        for(FireworkEffect effect : effects){

            ConfigurationSection current = eff.createSection(String.valueOf(index));
            current.set("Type", effect.getType().toString());

            if(effect.hasFlicker()){
                current.set("Flicker", "true");
            }else{
                current.set("Flicker", "false");
            }

            if(effect.hasTrail()){
                current.set("Trail", "true");
            }else{
                current.set("Trail", "false");
            }

            int colorIndex = 1;
            ConfigurationSection colors = current.createSection("Colors");
            for(Color c : effect.getColors()){

                colors.set(String.valueOf(colorIndex), c.asBGR());
                colorIndex = colorIndex + 1;
            }
            int colorFadeIndex = 1;
            ConfigurationSection colorsFade = current.createSection("FadeColors");
            for(Color c : effect.getFadeColors()){

                colorsFade.set(String.valueOf(colorFadeIndex), c.asBGR());
                colorFadeIndex = colorIndex + 1;
            }

            index = index + 1;

        }

    }

    public static FireworkMeta derserializeAdapt(ItemMeta meta, ConfigurationSection section){

        FireworkMeta fireworkMeta = (FireworkMeta) meta;
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        int power = sec.getInt("Power");

        List<FireworkEffect> effects = new ArrayList<>();

        for(String eff : sec.getConfigurationSection("Effects").getKeys(false)){

            ConfigurationSection current = sec.getConfigurationSection("Effects").getConfigurationSection(eff);
            String type = current.getString("Type");
            boolean flicker;
            boolean trail;

            if(current.getString("Flicker").equalsIgnoreCase("true")){
                flicker = true;
            }else {
                flicker = false;
            }
            if(current.getString("Trail").equalsIgnoreCase("true")){
                trail = true;
            }else {
                trail = false;
            }

            List<Color> colors = new ArrayList<>();

            for(String index : current.getConfigurationSection("Colors").getKeys(false)){

                Color c = Color.fromBGR(current.getConfigurationSection("Colors").getInt(index));
                colors.add(c);

            }

            List<Color> fadeColors = new ArrayList<>();

            for(String index : current.getConfigurationSection("FadeColors").getKeys(false)){

                Color c = Color.fromBGR(current.getConfigurationSection("FadeColors").getInt(index));
                fadeColors.add(c);

            }

            FireworkEffect.Builder inBuild = FireworkEffect.builder();
            for(Color c : colors){
                inBuild.withColor(c);
            }
            for(Color c : fadeColors){
                inBuild.withFade(c);
            }
            inBuild.trail(trail);
            inBuild.flicker(flicker);
            inBuild.with(FireworkEffect.Type.valueOf(type));
            FireworkEffect effect = inBuild.build();
            effects.add(effect);

        }

        for(FireworkEffect e : effects){
            fireworkMeta.addEffect(e);
        }

        fireworkMeta.setPower(power);

        return fireworkMeta;

    }

}
