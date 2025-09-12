package dev.insilicon.eventSystem;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class EventAdminCMD implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender.hasPermission("eventsys.admin");
    }

    @Override
    public @Nullable String permission() {
        return "eventsys.admin";
    }
}
