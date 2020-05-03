package ch.dams333.damsLib;

import ch.dams333.damsLib.adaptaters.*;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamsLIB extends JavaPlugin {

    public ItemStackManager itemStackManager;
    public TitleManager titleManager;
    public PlayerManager playerManager;

    @Override
    public void onEnable(){

        itemStackManager = new ItemStackManager();
        titleManager = new TitleManager();
        playerManager = new PlayerManager(this);


    }

    public int random(int min, int max){
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    public boolean isInt(String strNum) {
        boolean ret = true;
        try {

            Integer.parseInt(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    public boolean isFloat(String strNum) {
        boolean ret = true;
        try {

            Float.parseFloat(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    public boolean isDouble(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    @Override
    public void onDisable(){



    }

    public void serializeItem(ConfigurationSection section, ItemStack it){

        List<Material> adapaters = new ArrayList<>();
        adapaters.add(Material.LEATHER_BOOTS);
        adapaters.add(Material.LEATHER_CHESTPLATE);
        adapaters.add(Material.LEATHER_HELMET);
        adapaters.add(Material.LEATHER_LEGGINGS);
        adapaters.add(Material.WRITTEN_BOOK);
        adapaters.add(Material.POTION);
        adapaters.add(Material.SPLASH_POTION);
        adapaters.add(Material.LINGERING_POTION);

        List<Material> cannot = new ArrayList<>();

        int index = 0;
        String name = "SerializedItem0";
        if(section.getKeys(false).size() >= 1){
            index = section.getKeys(false).size() - 1;
            int num = index + 1;
            name = "SerializedItem" + num;
        }

        section.createSection(name);
        ConfigurationSection sec = section.getConfigurationSection(name);
        sec.set("Material", it.getType().toString());
        sec.set("Amount", it.getAmount());
        sec.set("Data", it.getData().getData());
        sec.createSection("ItemMeta");

        ConfigurationSection meta = sec.getConfigurationSection("ItemMeta");
        ItemMeta m = it.getItemMeta();
        meta.set("Name", m.getDisplayName());

        if(m.hasLore()) {
            meta.createSection("Lore");
            ConfigurationSection loreSec = meta.getConfigurationSection("Lore");
            int loreIndex = 1;
            for (String lore : m.getLore()) {

                loreSec.set(String.valueOf(loreIndex), lore);
                loreIndex = loreIndex + 1;

            }
        }

        if(m.hasEnchants()) {
            meta.createSection("Enchants");
            ConfigurationSection enchantSec = meta.getConfigurationSection("Enchants");
            Map<Enchantment, Integer> enchs = m.getEnchants();
            for (Enchantment ench : enchs.keySet()) {

                enchantSec.set(ench.getName(), enchs.get(ench));

            }
        }

        if(adapaters.contains(it.getType())){

            if(it.getType() == Material.LEATHER_BOOTS || it.getType() == Material.LEATHER_HELMET || it.getType() == Material.LEATHER_CHESTPLATE || it.getType() == Material.LEATHER_LEGGINGS){

                sec.set("Type", "Leather");
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta)it.getItemMeta();
                LeatherAdaptater.serializeAdapt(sec, leatherArmorMeta);

            }



            if(it.getType() == Material.WRITTEN_BOOK){

                sec.set("Type", "Written_Book");
                BookMeta bookMeta = (BookMeta) it.getItemMeta();
                WrittenBookAdaptater.serializeAdapt(sec, bookMeta);

            }


            if(it.getType() == Material.POTION || it.getType() == Material.SPLASH_POTION || it.getType() == Material.LINGERING_POTION){
                sec.set("Type", "Potion");
                PotionMeta potionMeta = (PotionMeta) it.getItemMeta();
                PotionAdaptater.serializeAdapt(sec, potionMeta);
            }

        }else{

            sec.set("Type", "Normal");

        }

    }

    public List<ItemStack> deserializeItems(ConfigurationSection section){

        List<ItemStack> items = new ArrayList<>();

        if(section.getKeys(false).size() >= 1){

            for(String i : section.getKeys(false)){

                ConfigurationSection item = section.getConfigurationSection(i);

                Material material = Material.getMaterial(item.getString("Material"));
                Integer amount = item.getInt("Amount");
                String data = item.getString("Data");

                ConfigurationSection meta = item.getConfigurationSection("ItemMeta");

                ItemStack it = new ItemStack(material, amount, Byte.valueOf(data));
                ItemMeta m = it.getItemMeta();

                if(meta.getKeys(false).contains("Name")) {
                    String name = meta.getString("Name");
                    m.setDisplayName(name);
                }
                List<String> lore = new ArrayList<>();
                if(meta.isConfigurationSection("Lore")) {
                    if (meta.getConfigurationSection("Lore").getKeys(false).size() >= 1) {
                        for (String lorePart : meta.getConfigurationSection("Lore").getKeys(false)) {
                            lore.add(meta.getConfigurationSection("Lore").getString(lorePart));
                        }
                    }
                }

                Map<Enchantment, Integer> enchantments = new HashMap<>();
                if(meta.isConfigurationSection("Enchants")) {
                    if (meta.getConfigurationSection("Enchants").getKeys(false).size() >= 1) {
                        for (String enchantment : meta.getConfigurationSection("Enchants").getKeys(false)) {
                            enchantments.put(Enchantment.getByName(enchantment), meta.getConfigurationSection("Enchants").getInt(enchantment));
                        }
                    }
                }

                m.setLore(lore);
                for(Enchantment en : enchantments.keySet()){
                    m.addEnchant(en, enchantments.get(en), true);
                }
                it.setItemMeta(m);

                if(!item.getString("Type").equalsIgnoreCase("Normal")){

                    String adaptater = item.getString("Type");

                    if(adaptater.equalsIgnoreCase("Leather")){

                        LeatherArmorMeta leatherArmorMeta = LeatherAdaptater.derserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(leatherArmorMeta);

                    }

                    if(adaptater.equalsIgnoreCase("Banner")){

                        BannerMeta bannerMeta = BannerAdaptater.derserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(bannerMeta);

                    }

                    if(adaptater.equalsIgnoreCase("Firework")){

                        FireworkMeta fireworkMeta = FireworkAdaptater.derserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(fireworkMeta);

                    }

                    if(adaptater.equalsIgnoreCase("Written_Book")){

                        BookMeta bookMeta = WrittenBookAdaptater.deserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(bookMeta);

                    }

                    if (adaptater.equalsIgnoreCase("Skull")) {

                        SkullMeta skullMeta = SkullAdaptater.derserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(skullMeta);

                    }

                    if(adaptater.equalsIgnoreCase("Spawn_Egg")){

                        SpawnEggMeta spawnEggMeta = SpawnEggAdaptater.derserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(spawnEggMeta);

                    }

                    if(adaptater.equalsIgnoreCase("Potion")){
                        PotionMeta potionMeta = PotionAdaptater.derserializeAdapt(it.getItemMeta(), item);
                        it.setItemMeta(potionMeta);
                    }

                }

                items.add(it);

            }

        }

        return items;
    }

}
