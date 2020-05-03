package ch.dams333.damsLib.adaptaters;

import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionAdaptater {

    public static void serializeAdapt(ConfigurationSection section, PotionMeta potionMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        if(potionMeta.hasColor()) {
            sec.createSection("Colors");
            sec.getConfigurationSection("Colors").set("Color", potionMeta.getColor().asBGR());
        }
        ConfigurationSection base = sec.createSection("Base");
        base.set("Type", potionMeta.getBasePotionData().getType().getEffectType().getName());
        base.set("Extended", potionMeta.getBasePotionData().isExtended());
        base.set("Upgraded", potionMeta.getBasePotionData().isUpgraded());
        if(potionMeta.hasCustomEffects()) {
            ConfigurationSection custom = sec.createSection("CustomEffects");
            for (PotionEffect effect : potionMeta.getCustomEffects()) {

                ConfigurationSection eff = custom.createSection(effect.getType().getName());
                eff.set("Amplifier", effect.getAmplifier());
                eff.set("Duration", effect.getDuration());

            }
        }

    }

    public static PotionMeta derserializeAdapt(ItemMeta meta, ConfigurationSection section){

        PotionMeta potionMeta = (PotionMeta) meta;
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        if(sec.isConfigurationSection("Colors")) {
            Color color = Color.fromBGR(sec.getConfigurationSection("Colors").getInt("Color"));
            potionMeta.setColor(color);
        }
        ConfigurationSection base = sec.getConfigurationSection("Base");
        PotionEffectType type = PotionEffectType.getByName(base.getString("Type"));
        boolean extended = base.getBoolean("Extended");
        boolean upgraded = base.getBoolean("Upgraded");

        PotionType potionType = PotionType.getByEffect(type);

        PotionData basePotionData = new PotionData(potionType, extended, upgraded);
        potionMeta.setBasePotionData(basePotionData);

        if(sec.isConfigurationSection("CustomEffects")){

            for(String effect : sec.getConfigurationSection("CustomEffects").getKeys(false)){

                ConfigurationSection effectSection = sec.getConfigurationSection("CustmEffects").getConfigurationSection(effect);
                int amplifier = effectSection.getInt("Amplifier");
                int duration = effectSection.getInt("Duration");

                PotionEffect potionEffect = new PotionEffect(PotionEffectType.getByName(effect), duration, amplifier);
                potionMeta.addCustomEffect(potionEffect, true);

            }

        }

        return potionMeta;

    }

}
