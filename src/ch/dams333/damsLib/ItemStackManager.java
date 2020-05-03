package ch.dams333.damsLib;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackManager {

    public boolean isHelmet(ItemStack it){
        if(it != null) {
            if (it.getType() == Material.LEATHER_HELMET) return true;
            if (it.getType() == Material.CHAINMAIL_HELMET) return true;
            if (it.getType() == Material.DIAMOND_HELMET) return true;
            if (it.getType() == Material.GOLDEN_HELMET) return true;
            if (it.getType() == Material.IRON_HELMET) return true;
        }
        return false;
    }

    public boolean isChestplate(ItemStack it){
        if(it != null) {
            if (it.getType() == Material.LEATHER_CHESTPLATE) return true;
            if (it.getType() == Material.CHAINMAIL_CHESTPLATE) return true;
            if (it.getType() == Material.DIAMOND_CHESTPLATE) return true;
            if (it.getType() == Material.GOLDEN_CHESTPLATE) return true;
            if (it.getType() == Material.IRON_CHESTPLATE) return true;
        }
        return false;
    }

    public boolean isLeggings(ItemStack it){
        if(it != null) {
            if (it.getType() == Material.LEATHER_LEGGINGS) return true;
            if (it.getType() == Material.CHAINMAIL_LEGGINGS) return true;
            if (it.getType() == Material.DIAMOND_LEGGINGS) return true;
            if (it.getType() == Material.GOLDEN_LEGGINGS) return true;
            if (it.getType() == Material.IRON_LEGGINGS) return true;
        }
        return false;
    }

    public boolean isBoots(ItemStack it){
        if(it != null) {
            if (it.getType() == Material.LEATHER_BOOTS) return true;
            if (it.getType() == Material.CHAINMAIL_BOOTS) return true;
            if (it.getType() == Material.DIAMOND_BOOTS) return true;
            if (it.getType() == Material.GOLDEN_BOOTS) return true;
            if (it.getType() == Material.IRON_BOOTS) return true;
        }
        return false;
    }

    public ItemStack create(Material material){
        return create(material, 1);
    }
    public ItemStack create(Material material, int quantity){
        return create(material, quantity, (byte) 0);
    }
    public ItemStack create(Material material, byte data){
        return create(material, 1, data);
    }
    public ItemStack create(Material material, int quantity, byte data){
        return new ItemStack(material, quantity, data);
    }
    public ItemStack create(Material material, String name){
        return create(material, 1, (byte) 0, name);
    }
    public ItemStack create(Material material, int quantity, String name){
        return create(material, quantity, (byte) 0, name);
    }
    public ItemStack create(Material material, byte data, String name){
        return create(material, 1, data, name);
    }
    public static ItemStack create(Material material, int quantity, byte data, String name){
        ItemStack it = new ItemStack(material, quantity, data);
        ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        it.setItemMeta(itM);
        return it;
    }
    public ItemStack create(Material material, List<String> lore){
        return create(material, 1, (byte) 0, lore);
    }
    public ItemStack create(Material material, int quantity, List<String> lore){
        return create(material, quantity, (byte) 0, lore);
    }
    public ItemStack create(Material material, byte data, List<String> lore){
        return create(material, 1, data, lore);
    }
    public ItemStack create(Material material, String name, List<String> lore){
        return create(material, 1, (byte) 0, name, lore);
    }
    public ItemStack create(Material material, int quantity, byte data, List<String> lore){
        ItemStack it = new ItemStack(material, quantity, data);
        ItemMeta itM = it.getItemMeta();
        itM.setLore(lore);
        it.setItemMeta(itM);
        return it;
    }
    public ItemStack create(Material material, int quantity, String name, List<String> lore){
        return create(material, quantity, (byte) 0, name, lore);
    }
    public ItemStack create(Material material, byte data, String name, List<String> lore){
        return create(material, 1, data, name, lore);
    }
    public ItemStack create(Material material, int quantity, byte data, String name, List<String> lore){
        ItemStack it = new ItemStack(material, quantity, data);
        ItemMeta itM = it.getItemMeta();
        itM.setDisplayName(name);
        itM.setLore(lore);
        it.setItemMeta(itM);
        return it;
    }

}
