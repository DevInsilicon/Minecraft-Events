package dev.insilicon.eventSystem.Events;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class BaseEvent implements Listener {

    private Component displayName;
    private String id;

    public BaseEvent(Component displayName, String id) {

    }


    public void initEvent() {

    }

    public void startEvent() {

    }

    public void endEvent() {
        cleanUp();
    }

    public void cleanUp() {

    }


    public void playerDiedInEvent(Player player) {

    }

    public void playerJoinedEvent(Player player) {

    }

    public boolean canPlayerJoinEvent() {
        return false;
    }



    public void tick() {

    }

    public void second() {

    }

    public void minute() {

    }

}
