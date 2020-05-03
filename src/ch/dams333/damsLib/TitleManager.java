package ch.dams333.damsLib;

import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleManager {

    public void sendTitle(Player player, String title, int ticks) {

        IChatBaseComponent baseTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, baseTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(titlePacket);

        sendTime(player, ticks);

    }

    public void sendSubTitle(Player player, String subTitle, int ticks){

        IChatBaseComponent baseSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, baseSubTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitlePacket);

        sendTime(player, ticks);

    }

    public void sendActionBar(Player player, String actionBar, int ticks){

        IChatBaseComponent baseSubTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + actionBar + "\"}");
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, baseSubTitle);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(subTitlePacket);

        sendTime(player, ticks);

    }

    private void sendTime(Player player, int ticks) {

        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 20, ticks, 20);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(titlePacket);

    }

}
