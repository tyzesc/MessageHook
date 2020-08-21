package tyze.bukkit.messagehook.Kycraft;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KycraftEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String playerName;
    private String itemName;

    public KycraftEvent(String playerName, String itemName) {
        this.playerName = playerName;
        this.itemName = itemName;
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

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}