package tyze.bukkit.messagehook;

import java.util.Map;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;
import tyze.bukkit.messagehook.Kycraft.KycraftEvent;
import tyze.bukkit.messagehook.Kycraft.KycraftListener;
import tyze.bukkit.messagehook.LotteryItem.LotteryItemEvent;
import tyze.bukkit.messagehook.LotteryItem.LotteryItemListener;

public class MessageHook extends JavaPlugin implements Listener {
    PluginManager pluginManager;
    ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        getLogger().info("聊天掛勾插件啟動");
        pluginManager = getServer().getPluginManager();
        protocolManager = ProtocolLibrary.getProtocolManager();

        pluginManager.registerEvents(this, this);
        pluginManager.registerEvents(new KycraftListener(pluginManager), this);
        protocolManager.addPacketListener(new LotteryItemListener(pluginManager, this));
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

    @EventHandler
    public void onLotteryItemAnnounce(LotteryItemEvent event) {
        try {
            String channel_id = DiscordSRV.getPlugin().getChannels().entrySet().stream()
                    .filter(e -> e.getKey().equals("li_celebrate")).map(Map.Entry::getValue).findFirst().orElse(null);
            TextChannel channel = (channel_id != null ? DiscordUtil.getTextChannelById(channel_id)
                    : DiscordSRV.getPlugin().getMainTextChannel());
            String string = ":gift: **%playername%** 在 **%packagename%** 中抽出 [**%itemname%** × **%itemnum%**]";
            string = string.replace("%playername%", event.getPlayerName());
            string = string.replace("%packagename%", event.getRawPackageName());
            string = string.replace("%itemname%", event.getRawItemName());
            string = string.replace("%itemnum%", event.getItemNum());
            DiscordUtil.sendMessage(channel, string);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}