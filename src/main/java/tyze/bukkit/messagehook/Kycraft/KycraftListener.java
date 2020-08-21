package tyze.bukkit.messagehook.Kycraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.plugin.PluginManager;

public class KycraftListener implements Listener {
    private PluginManager pluginManager;
    private String regex = "§f【§4系統公告§f】(?<player>.+?)§6精煉§b(?<item>.+?)§f成功！";

    public KycraftListener(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBroadcastMessageEvent(BroadcastMessageEvent event) {
        String s = event.getMessage();
        Pattern pattern = Pattern.compile(regex);
        Matcher mathcer = pattern.matcher(s);
        if (mathcer.matches()) {
            KycraftEvent kycEvent = new KycraftEvent(mathcer.group("player"), mathcer.group("item"));
            pluginManager.callEvent(kycEvent);
        }
    }
}