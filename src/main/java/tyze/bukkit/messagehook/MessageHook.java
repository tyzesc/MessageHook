package tyze.bukkit.messagehook;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import tyze.bukkit.messagehook.Kycraft.KycraftEvent;
import tyze.bukkit.messagehook.Kycraft.KycraftListener;

public class MessageHook extends JavaPlugin implements Listener {
    PluginManager pluginManager;

    @Override
    public void onEnable() {
        getLogger().info("聊天掛勾插件啟動");
        pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(this, this);
        pluginManager.registerEvents(new KycraftListener(pluginManager), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("聊天掛勾插件關閉");
    }

    @EventHandler
    public void onKycCraftSuccess(KycraftEvent event) {
        try {
            String channel_id = DiscordSRV.getPlugin().getChannels().entrySet().stream()
                    .filter(e -> e.getKey().equals("kyc_celebrate")).map(Map.Entry::getValue).findFirst().orElse(null);
            TextChannel channel = (channel_id != null ? DiscordUtil.getTextChannelById(channel_id)
                    : DiscordSRV.getPlugin().getMainTextChannel());
            String string = ":crafting_table: **%playername%** 成功精煉出 **%itemname%**";
            string = string.replace("%playername%", event.getPlayerName());
            string = string.replace("%itemname%", event.getRawItemName());
            DiscordUtil.sendMessage(channel, string);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}