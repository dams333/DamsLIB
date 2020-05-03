package ch.dams333.damsLib.adaptaters;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class BannerAdaptater {

    public static void serializeAdapt(ConfigurationSection section, BannerMeta bannerMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        int index = 1;

        List<Pattern> patterns = bannerMeta.getPatterns();

        for(Pattern pattern : patterns){

            PatternType type = pattern.getPattern();
            DyeColor c = pattern.getColor();
            String color = c.toString();
            String p = type.name();

            sec.createSection(String.valueOf(index));
            ConfigurationSection save = sec.getConfigurationSection(String.valueOf(index));
            save.set(p, color);
            index = index + 1;

        }


    }

    public static BannerMeta derserializeAdapt(ItemMeta meta, ConfigurationSection section){

        BannerMeta bannerMeta = (BannerMeta) meta;
        List<Pattern> patterns = new ArrayList<>();
        for(String index : section.getConfigurationSection("Adaptater").getKeys(false)){
            for(String pattern : section.getConfigurationSection("Adaptater").getConfigurationSection(index).getKeys(false)){

                ConfigurationSection indexSection = section.getConfigurationSection("Adaptater").getConfigurationSection(index);
                PatternType p = PatternType.valueOf(pattern);
                String c = indexSection.getString(pattern);
                DyeColor color = DyeColor.valueOf(c);
                Pattern pat = new Pattern(color, p);
                patterns.add(pat);

            }
        }

        bannerMeta.setPatterns(patterns);

        return bannerMeta;

    }

}
