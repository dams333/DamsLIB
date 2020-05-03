package ch.dams333.damsLib.adaptaters;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WrittenBookAdaptater {

    public static void serializeAdapt(ConfigurationSection section, BookMeta bookMeta){

        section.createSection("Adaptater");
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        String title = bookMeta.getTitle();
        String author = bookMeta.getAuthor();
        List<String> pages = bookMeta.getPages();
        sec.set("Title", title);
        sec.set("Author", author);
        sec.createSection("Pages");
        int index = 1;
        for(String string : pages){
            sec.getConfigurationSection("Pages").set(String.valueOf(index), string);
            index = index + 1;
        }

    }

    public static BookMeta deserializeAdapt(ItemMeta meta, ConfigurationSection section){

        BookMeta bookMeta = (BookMeta) meta;
        ConfigurationSection sec = section.getConfigurationSection("Adaptater");

        String title = sec.getString("Title");
        String author = sec.getString("Author");

        List<String> pages = new ArrayList<>();
        for(String index : sec.getConfigurationSection("Pages").getKeys(false)){
            pages.add(sec.getConfigurationSection("Pages").getString(index));
        }

        bookMeta.setAuthor(author);
        bookMeta.setTitle(title);
        bookMeta.setPages(pages);

        return bookMeta;

    }

}
