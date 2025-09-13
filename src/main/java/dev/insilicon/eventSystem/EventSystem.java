package dev.insilicon.eventSystem;

import com.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class EventSystem extends JavaPlugin {

    public static EventSystem instance;

    @Override
    public void onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onEnable() {

        instance = this;
        PacketEvents.getAPI().init();

        getDataFolder().mkdir();
        new InventoryManagement();

        new EventManager();
        getServer().getPluginManager().registerEvents(EventManager.instance, this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, (events) -> {

            events.registrar().register(
                    "event",
                    new EventCMD()
            );

            events.registrar().register(
                    "eadmin",
                    new EventAdminCMD()
            );

        });

    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
    }
}
