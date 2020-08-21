package tyze.bukkit.messagehook.LotteryItem;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import net.md_5.bungee.api.chat.BaseComponent;

public class LotteryItemListener extends PacketAdapter {
    PluginManager pluginManager;
    private String regex = "§7\\[§6LotteryItem§7\\]§r §a玩家§7<§f(?<player>.+?)§7>§a在§7\\[(?<package>.+?)\\]§a抽中了§7\\[(?<item>.+?) × (?<num>.+?)§7\\]§a。";

    public LotteryItemListener(PluginManager pluginManager, Plugin plugin) {
        super(plugin, ListenerPriority.MONITOR, PacketType.Play.Server.CHAT);
        this.pluginManager = pluginManager;
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        try {
            PacketContainer packet = event.getPacket();
            BaseComponent[] arr = packet.getSpecificModifier(BaseComponent[].class).readSafely(0);

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(BaseComponent.toPlainText(arr));
            if (matcher.matches()) {
                LotteryItemEvent e = new LotteryItemEvent(matcher.group("player"), matcher.group("package"),
                        matcher.group("item"), matcher.group("num"));
                if (event.getPlayer().getDisplayName().equals(matcher.group("player")))
                    pluginManager.callEvent(e);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

/**
 * Ref: dmulloy2 [at] github
 * 
 * https://github.com/aadnk/ProtocolLib/issues/118
 * https://www.spigotmc.org/threads/nullpointer-on-chat-listener-protocollib.415795/
 * 
 * It's related to ProtocolLib, yes, but it's caused by how Spigot sends custom
 * chat components. Basically how it works is Spigot uses their own
 * implementation (from Bungee) and a separate variable. The packet ends up
 * looking like this:
 * 
 * private IChatBaseComponent a; public net.md_5.bungee.api.chat.BaseComponent[]
 * components; // Spigot private byte b;
 * 
 * With that said, you can still modify the Spigot components with
 * packet.getSpecificModifier(BaseComponent[].class).readSafely(0). Note that
 * Spigot components take priority over vanilla MC components.
 */