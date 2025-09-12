package dev.insilicon.eventSystem.Events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.joml.Vector3d;

public class PVPEvent extends BaseEvent {

    private static Vector3d copy = new Vector3d(3,66,106);
    private static Vector3d copyb = new Vector3d(101,164,204);

    public PVPEvent() {
        super(MiniMessage.miniMessage().deserialize("<red>PVP Event</red>"), "pvpevent");



    }

    public void spawnArena(){



    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void startEvent() {
        super.startEvent();
    }

    @Override
    public void endEvent() {
        super.endEvent();
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
    }

    @Override
    public void playerDiedInEvent(Player player) {
        super.playerDiedInEvent(player);
    }

    @Override
    public void playerJoinedEvent(Player player) {
        super.playerJoinedEvent(player);
    }

    @Override
    public boolean canPlayerJoinEvent() {
        return super.canPlayerJoinEvent();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void second() {
        super.second();
    }

    @Override
    public void minute() {
        super.minute();
    }
}
