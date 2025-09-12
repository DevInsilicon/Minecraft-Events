package dev.insilicon.eventSystem;

import java.sql.Connection;

public class InventoryManagement {

    public static InventoryManagement instance;

    private static Connection database;

    public InventoryManagement() {
        instance = this;

        

    }

}
