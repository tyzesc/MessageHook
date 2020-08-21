package tyze.bukkit.messagehook.LotteryItem;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LotteryItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String playerName;
    private String itemName;
    private String packageName;
    private String itemNum;

    public LotteryItemEvent(String playerName, String packageName, String itemName, String itemNum) {
        this.playerName = playerName;
        this.itemName = itemName;
        this.packageName = packageName;
        this.itemNum = itemNum;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getRawItemName() {
        return itemName.replaceAll("§.", "");
    }

    public String getPackageName() {
        return packageName;
    }

    public String getRawPackageName() {
        return packageName.replaceAll("§.", "");
    }

    public String getItemNum() {
        return itemNum;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}