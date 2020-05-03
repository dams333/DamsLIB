package ch.dams333.damsLib;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.net.URL;

public class PlayerManager {
    DamsLIB main;
    public PlayerManager(DamsLIB damsLIB) {
        this.main = damsLIB;
    }

    public String getPseudoFromUUID(String uuid) {
        if(main.getConfig().isConfigurationSection("PlayersUUID")){
            if(main.getConfig().getConfigurationSection("PlayersUUID").getKeys(false).contains(uuid)){
                String pseudo = main.getConfig().getConfigurationSection("PlayersUUID").getString(uuid);
                return pseudo;
            }
        }
        String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";
        try {
            @SuppressWarnings("deprecation")
            String nameJson = IOUtils.toString(new URL(url));
            JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);
            String playerSlot = nameValue.get(nameValue.size()-1).toString();
            JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
            String pseudo = nameObject.get("name").toString();
            if(!main.getConfig().isConfigurationSection("PlayersUUID")){
                main.getConfig().createSection("PlayersUUID");
            }
            main.getConfig().getConfigurationSection("PlayersUUID").set(uuid, pseudo);
            main.saveConfig();
            return pseudo;
        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return "error";
    }

}
