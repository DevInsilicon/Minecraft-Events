package dev.insilicon.eventSystem;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.joml.Vector3d;

public class EventManager implements Listener {

    public static EventManager instance;

    public static Vector3d posAEventBlock = new Vector3d(3,66,3);
    public static Vector3d posBEventBlock = new Vector3d(101,164,101);
    public static String eventWorldName = "eventworld";

    public EventManager() {
        instance = this;


    }

}
