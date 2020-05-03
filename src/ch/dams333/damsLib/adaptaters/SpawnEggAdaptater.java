package ch.dams333.damsLib.adaptaters;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class SpawnEggAdaptater {

    public static void serializeAdapt(ConfigurationSection section, SpawnEggMeta spawnEggMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        sec.set("Spawn", spawnEggMeta.getSpawnedType().getTypeId());

    }

    public static SpawnEggMeta derserializeAdapt(ItemMeta meta, ConfigurationSection section){

        SpawnEggMeta spawnEggMeta = (SpawnEggMeta) meta;

        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        spawnEggMeta.setSpawnedType(EntityType.fromId(sec.getInt("Spawn")));

        return spawnEggMeta;

    }

}
