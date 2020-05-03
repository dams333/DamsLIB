package ch.dams333.damsLib.adaptaters;

import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherAdaptater {

    public static void serializeAdapt(ConfigurationSection section, LeatherArmorMeta leatherArmorMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");
        sec.set("Color", leatherArmorMeta.getColor().asBGR());

    }

    public static LeatherArmorMeta derserializeAdapt(ItemMeta meta, ConfigurationSection section){

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;

        ConfigurationSection sec = section.getConfigurationSection("Adaptater");
        int rgb = sec.getInt("Color");
        leatherArmorMeta.setColor(Color.fromBGR(rgb));

        return leatherArmorMeta;

    }

}
