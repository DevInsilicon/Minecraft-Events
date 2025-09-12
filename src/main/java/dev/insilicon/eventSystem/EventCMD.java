package dev.insilicon.eventSystem;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class EventCMD implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of("join", "leave");
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return true;
    }

    @Override
    public @Nullable String permission() {
        return "eventsys.participant";
    }
}
