package ch.dams333.damsLib.adaptaters;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullAdaptater {

    public static void serializeAdapt(ConfigurationSection section, SkullMeta skullMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        sec.set("Owner", skullMeta.getOwner());

    }

    public static SkullMeta derserializeAdapt(ItemMeta meta, ConfigurationSection section){

        SkullMeta skullMeta = (SkullMeta) meta;

        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(sec.getString("Owner")));

        return skullMeta;

    }

}
