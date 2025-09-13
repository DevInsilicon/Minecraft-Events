package dev.insilicon.eventSystem.Events;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.particle.Particle;
import com.github.retrooper.packetevents.protocol.particle.data.ParticleDustData;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleType;
import com.github.retrooper.packetevents.protocol.particle.type.ParticleTypes;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerParticle;
import dev.insilicon.eventSystem.EventManager;
import dev.insilicon.eventSystem.EventSystem;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Vector3d;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PVPEvent extends BaseEvent {

    private static Vector3d copy = new Vector3d(3,66,106);
    private static Vector3d copyb = new Vector3d(101,164,204);


    private boolean EventClosed = true;
    private boolean allowPVP = false;
    private long eventStartTime = 0L;
    private Vector3d borderCenter = null;
    private boolean startShowingBorder = false;

    private List<BukkitTask> cancelableTasks = new ArrayList<>();

    public PVPEvent() {
        super(MiniMessage.miniMessage().deserialize("<red>PVP Event</red>"), "pvpevent");



    }

    public void spawnArena(){
        Vector3d differences = new Vector3d(Math.abs(copyb.x - copy.x), Math.abs(copyb.y - copy.y), Math.abs(copyb.z - copy.z));
        Vector3d copyToPosA = EventManager.posAEventBlock;
        Vector3d copyToPosB = EventManager.posBEventBlock;

        BukkitScheduler scheduler = EventSystem.instance.getServer().getScheduler();

        int timeToTake = 3;
        int totalBlocks = (int)(differences.x * differences.y * differences.z);
        int blocksPerTick = Math.max(1, totalBlocks / (timeToTake * 20));

        World world = Bukkit.getWorld(EventManager.eventWorldName);

        scheduler.runTaskTimer(EventSystem.instance, new Runnable() {
            int currentBlock = 0;

            @Override
            public void run() {
                int blocksPlaced = 0;

                for (int i = 0; i < differences.x && blocksPlaced < blocksPerTick; i++) {
                    for (int ii = 0; ii < differences.y && blocksPlaced < blocksPerTick; ii++) {
                        for (int iii = 0; iii < differences.z && blocksPlaced < blocksPerTick; iii++) {
                            if (currentBlock >= totalBlocks) return;

                            Location sourceLocation = new Location(world, copy.x + i, copy.y + ii, copy.z + iii);
                            Location targetLocation = new Location(world, copyToPosA.x + i, copyToPosA.y + ii, copyToPosA.z + iii);

                            targetLocation.getBlock().setType(sourceLocation.getBlock().getType());
                            targetLocation.getBlock().setBlockData(sourceLocation.getBlock().getBlockData());

                            currentBlock++;
                            blocksPlaced++;
                        }
                    }
                }

                if (currentBlock >= totalBlocks) {
                    scheduler.cancelTask(this.hashCode());
                }
            }
        }, 0L, 1L);
    }

    @Override
    public void initEvent() {
        spawnArena();
        EventClosed = true;

        List<Component> messageLines = List.of(
            MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
            MiniMessage.miniMessage().deserialize(""),
            MiniMessage.miniMessage().deserialize("<yellow>A <red>PVP EVENT<yellow> is starting."),
            MiniMessage.miniMessage().deserialize("<yellow>It will begin in 3 minutes."),
            MiniMessage.miniMessage().deserialize("<yellow>Use /event join to participate."),
            MiniMessage.miniMessage().deserialize(""),
            MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
        );

        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Component line : messageLines) {
                player.sendMessage(line);
            }
        }

        BukkitScheduler scheduler = EventSystem.instance.getServer().getScheduler();

        scheduler.runTaskLater(EventSystem.instance, () -> {

            List<Component> messageLines2 = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow>A <red>PVP EVENT<yellow> is starting."),
                    MiniMessage.miniMessage().deserialize("<yellow>It will begin in 2 minutes."),
                    MiniMessage.miniMessage().deserialize("<yellow>Use /event join to participate."),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );

            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Component line : messageLines2) {
                    player.sendMessage(line);
                }
            }

        }, 20*60L);

        scheduler.runTaskLater(EventSystem.instance, () -> {

            List<Component> messageLines3 = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow>A <red>PVP EVENT<yellow> is starting."),
                    MiniMessage.miniMessage().deserialize("<yellow>It will begin in 1 minute."),
                    MiniMessage.miniMessage().deserialize("<yellow>Use /event join to participate."),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );

            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Component line : messageLines3) {
                    player.sendMessage(line);
                }
            }

        }, 20*120L);

        scheduler.runTaskLater(EventSystem.instance, () -> {
            EventClosed = true;

            List<Component> messageLines3 = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow>The <red>PVP EVENT<yellow> has <red>closed<yellow>."),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );

            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Component line : messageLines3) {
                    player.sendMessage(line);
                }
            }

            startEvent();
        }, 20*180L);

    }

    private void giveKits() {

        // Kit - diamond armor prot 4, unbreaking 3, sharp 5 diamond sword, reg bow, 64 arrows, 64 cooked beef, 3 golden apples, 1 shield, 1 d axe
        ItemStack Helmet = new ItemStack(Material.DIAMOND_HELMET);
        Helmet.editMeta(itemMeta -> {
            itemMeta.addEnchant(Enchantment.PROTECTION, 4, true);
            itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        });

        ItemStack Chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        Chestplate.editMeta(itemMeta -> {
            itemMeta.addEnchant(Enchantment.PROTECTION, 4, true);
            itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        });

        ItemStack Leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        Leggings.editMeta(itemMeta -> {
            itemMeta.addEnchant(Enchantment.PROTECTION, 4, true);
            itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        });

        ItemStack Boots = new ItemStack(Material.DIAMOND_BOOTS);
        Boots.editMeta(itemMeta -> {
            itemMeta.addEnchant(Enchantment.PROTECTION, 4, true);
            itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        });

        ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
        Sword.editMeta(itemMeta -> {
            itemMeta.addEnchant(Enchantment.SHARPNESS, 5, true);
            itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        });

        ItemStack Bow = new ItemStack(Material.BOW);
        ItemStack Arrows = new ItemStack(Material.ARROW, 64);
        ItemStack Beef = new ItemStack(Material.COOKED_BEEF, 64);
        ItemStack GoldenApples = new ItemStack(Material.GOLDEN_APPLE, 3);
        ItemStack Shield = new ItemStack(Material.SHIELD);
        ItemStack Axe = new ItemStack(Material.DIAMOND_AXE);

        for (Player player : playersInEvent) {
            player.getInventory().clear();
            player.getInventory().setHelmet(Helmet);
            player.getInventory().setChestplate(Chestplate);
            player.getInventory().setLeggings(Leggings);
            player.getInventory().setBoots(Boots);
            player.getInventory().addItem(Sword);
            player.getInventory().addItem(Bow);
            player.getInventory().addItem(Arrows);
            player.getInventory().addItem(Beef);
            player.getInventory().addItem(GoldenApples);
            player.getInventory().addItem(Shield);
            player.getInventory().addItem(Axe);
            player.updateInventory();
        }
    }

    @Override
    public void startEvent() {

        World eventWorld = Bukkit.getWorld(EventManager.eventWorldName);

        for (Player player : playersInEvent) {
            player.teleport(new Location(eventWorld,51,103,51));
            player.sendRichMessage("<red>The PVP event is starting in 15 seconds. Prepare to fight!<red>");
        }

        giveKits();

        BukkitScheduler scheduler = EventSystem.instance.getServer().getScheduler();


        cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, () -> {
            allowPVP = true;

            for (Player player : playersInEvent) {
                Title title = Title.title(
                        MiniMessage.miniMessage().deserialize("<color:#ffdf40>FIGHT!"),
                        MiniMessage.miniMessage().deserialize("<color#ffb940>You can now attack other players."),
                        Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(2), Duration.ofMillis(500))
                );
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }

        }, 20*15L));

        // countdown
        for (int i = 1; i <= 15; i++) {
            int delay = 20 * i;
            int finalI = i;
            cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, () -> {
                for (Player player : playersInEvent) {
                    if (finalI > 5) {
                        player.sendActionBar(MiniMessage.miniMessage().deserialize("<color:#ff4640>The event starts in <color:#ffdc40>" + (16 - finalI) + " seconds<red>!"));
                    } else {
                        Title title = Title.title(
                                MiniMessage.miniMessage().deserialize("<color:#ff7c40>" + (16 - finalI)),
                                MiniMessage.miniMessage().deserialize("<color:#ff6640>Get ready to fight!"),
                                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(1), Duration.ofMillis(500))
                        );
                        player.showTitle(title);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 0.5f + (finalI * 0.1f));
                    }
                }
            }, delay));

        }

        // sched for in 15 sec
        // YES I KNOW ITS INEFFICIENT BUT ITS FOR THE SAKE OF READABLE CODE
        cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, ()-> {
            allowPVP = true;
            eventStartTime = System.currentTimeMillis();

            for (Player player : playersInEvent) {
                Location loc = player.getLocation();
                loc.setY(loc.getY() + 1);
                Firework firework = player.getWorld().spawn(loc, Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.setPower(1);
                meta.addEffect(FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).build());
                firework.setFireworkMeta(meta);
            }

        }, 20*15L));


        // around 5 min mark start shrinking border
        cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, ()-> {

            // Calculate center of the event area
            Vector3d copyToPosA = EventManager.posAEventBlock;
            Vector3d copyToPosB = EventManager.posBEventBlock;
            Vector3d center = new Vector3d(
                (copyToPosA.x + copyToPosB.x) / 2.0,
                (copyToPosA.y + copyToPosB.y) / 2.0,
                (copyToPosA.z + copyToPosB.z) / 2.0
            );
            borderCenter = center;
            startShowingBorder = true;

            List<Component> messageLines = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow>The event border is now shrinking!"),
                    MiniMessage.miniMessage().deserialize("<yellow>Move to the center to avoid damage."),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );

            for (Player player : playersInEvent) {
                for (Component line : messageLines) {
                    player.sendMessage(line);
                }

                Location loc = player.getLocation();
                loc.setY(loc.getY() + 1);
                Firework firework = player.getWorld().spawn(loc, Firework.class);
                FireworkMeta meta = firework.getFireworkMeta();
                meta.setPower(1);
                meta.addEffect(FireworkEffect.builder().withColor(Color.BLUE).with(FireworkEffect.Type.BALL_LARGE).build());
                firework.setFireworkMeta(meta);
            }



        }, 20*300L));

        // 5min 30 sec
        cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, ()-> {

            List<Component> messageLines = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<red><bold>Teaming is no longer allowed."),
                    MiniMessage.miniMessage().deserialize("<yellow>Continuing to team will result in a event ban."),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );

            for (Player player : playersInEvent) {
                for (Component line : messageLines) {
                    player.sendMessage(line);
                }
            }

        }, 20*330L));

        // after 9 min
        cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, ()-> {

            List<Component> messageLines = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow>The event is ending in 1 minute!"),
                    MiniMessage.miniMessage().deserialize("<yellow>Last chance to eliminate other players!"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );

            for (Player player : playersInEvent) {
                for (Component line : messageLines) {
                    player.sendMessage(line);
                }
            }

        }, 20*540L));

        // after 10 min
        cancelableTasks.add(scheduler.runTaskLater(EventSystem.instance, ()-> {

            for (Player player : playersInEvent) {
                player.setHealth(0);
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                List<Component> messageLines = List.of(
                        MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                        MiniMessage.miniMessage().deserialize(""),
                        MiniMessage.miniMessage().deserialize("<yellow>The event has ended!"),
                        MiniMessage.miniMessage().deserialize("<yellow>Thanks for participating!"),
                        MiniMessage.miniMessage().deserialize(""),
                        MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
                );

                for (Component line : messageLines) {
                    player.sendMessage(line);
                }
            }

        }, 20*600L));

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!startShowingBorder) return;

        if (player.getWorld().getName() == EventManager.eventWorldName) return;
        if (!playersInEvent.contains(player)) return;


        // calculate distance from center by time. It should be a 5x5 at the 9 min mark
        if (startShowingBorder && borderCenter != null) {

            long elapsedTime = System.currentTimeMillis() - eventStartTime;
            double totalDuration = 10 * 60 * 1000;
            double initialRadius = 100.0;
            double finalRadius = 5.0;
            double currentRadius = initialRadius - ((initialRadius - finalRadius) * (elapsedTime / totalDuration));
            currentRadius = Math.max(finalRadius, currentRadius);
            Vector3d playerPos = new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
            double distanceFromCenter = playerPos.distance(borderCenter);


            if (distanceFromCenter > currentRadius) {
                player.sendRichMessage("<red>You are outside the event border! Move to the center to avoid damage.<red>");
                return;
            }


            if (distanceFromCenter > currentRadius - 5) {
                World world = player.getWorld();
                Location playerLocation = player.getLocation();

                User user = PacketEvents.getAPI().getPlayerManager().getUser(player);
                if (user == null) {
                    System.out.println("player isnt user?");
                    return;
                }

                for (double angle = 0; angle < 360; angle += 10) {
                    double radians = Math.toRadians(angle);
                    double x = borderCenter.x + currentRadius * Math.cos(radians);
                    double z = borderCenter.z + currentRadius * Math.sin(radians);
                    Location particleLocation = new Location(world, x, playerLocation.getY(), z);
                    if (particleLocation.distance(playerLocation) <= 20) {


                        com.github.retrooper.packetevents.util.Vector3d pos = new com.github.retrooper.packetevents.util.Vector3d(particleLocation.getX(), particleLocation.getY(), particleLocation.getZ());
                        Vector3f offset = new Vector3f(0.1f, 0.1f, 0.1f);
                        ParticleDustData dataDust = new ParticleDustData(1.0f, 0.0f, 0.0f, 1.0f);
                        Particle particle = new Particle(ParticleTypes.DUST, dataDust);

                        WrapperPlayServerParticle particlePacket = new WrapperPlayServerParticle(
                                particle, true, pos, offset, 0.0f, 1
                        );

                        user.sendPacket(particlePacket);

                    }
                }
            }

        }
    }

    @EventHandler
    public void onPlayerDamage(PrePlayerAttackEntityEvent event) {
        Player attacker = event.getPlayer();

        if (playersInEvent.contains(attacker)) {
            if (!allowPVP) {
                event.setCancelled(true);
                attacker.sendRichMessage("<red>You cannot attack yet! Wait for the event to start.<red>");
                attacker.playSound(attacker.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
            }
        }
    }

    @Override
    public void endEvent() {
        cleanUp();
    }

    @Override
    public void cleanUp() {

        for (BukkitTask task : cancelableTasks) {
            if (!task.isCancelled() || task == null) {
                task.cancel();
            }
        }
        cancelableTasks.clear();
        allowPVP = false;
        Vector3d copyToPosA = EventManager.posAEventBlock;
        Vector3d copyToPosB = EventManager.posBEventBlock;
        Vector3d differences = new Vector3d(Math.abs(copyToPosB.x - copyToPosA.x), Math.abs(copyToPosB.y - copyToPosA.y), Math.abs(copyToPosB.z - copyToPosA.z));
        World world = Bukkit.getWorld(EventManager.eventWorldName);
        BukkitScheduler scheduler = EventSystem.instance.getServer().getScheduler();
        int timeToTake = 10;
        int totalBlocks = (int)(differences.x * differences.y * differences.z);
        int blocksPerTick = Math.max(1, totalBlocks / (timeToTake * 20));
        scheduler.runTaskTimer(EventSystem.instance, new Runnable() {
            int currentBlock = 0;

            @Override
            public void run() {
                int blocksPlaced = 0;

                for (int i = 0; i < differences.x && blocksPlaced < blocksPerTick; i++) {
                    for (int ii = 0; ii < differences.y && blocksPlaced < blocksPerTick; ii++) {
                        for (int iii = 0; iii < differences.z && blocksPlaced < blocksPerTick; iii++) {
                            if (currentBlock >= totalBlocks) return;

                            Location targetLocation = new Location(world, copyToPosA.x + i, copyToPosA.y + ii, copyToPosA.z + iii);

                            targetLocation.getBlock().setType(Material.AIR);

                            currentBlock++;
                            blocksPlaced++;
                        }
                    }
                }

                if (currentBlock >= totalBlocks) {
                    scheduler.cancelTask(this.hashCode());
                }
            }
        }, 0L, 1L);

        // when done clearing arena
        scheduler.runTaskLater(EventSystem.instance, ()-> {
            // EventManager.instance.officallyEndEvent();
            EventClosed = true;
            startShowingBorder = false;
            borderCenter = null;
            eventStartTime = 0L;
            for (Player player : playersInEvent) {

                EventManager.instance.kickPlayerFromEvent(player);
                player.sendRichMessage("<red>You have been removed from the event.<red>");
            }
        }, 20*11L);

    }

    @Override
    public void playerDiedInEvent(Player player) {
        player.sendRichMessage("<red>You have died and been removed from the event.<red>");
        EventManager.instance.kickPlayerFromEvent(player);

        if (playersInEvent.size() == 1) {
            Player winner = playersInEvent.get(0);
            EventManager.instance.kickPlayerFromEvent(winner);

            List<Component> messageLines = List.of(
                    MiniMessage.miniMessage().deserialize("<yellow><---------------- EVENTS ---------------->"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow>The event has ended!"),
                    MiniMessage.miniMessage().deserialize("<gold><bold>" + winner.getName() + " <yellow>is the winner of the PVP event!"),
                    MiniMessage.miniMessage().deserialize(""),
                    MiniMessage.miniMessage().deserialize("<yellow><---------------------------------------->")
            );


            for (Player p : Bukkit.getOnlinePlayers()) {

                for (Component line : messageLines) {
                    p.sendMessage(line);
                }

            }
            endEvent();
        }
    }

    @Override
    public void playerJoinedEvent(Player player) {
        player.sendRichMessage("<red>You have joined the PVP Event!<red>");
    }

    @Override
    public boolean canPlayerJoinEvent() {
        return EventClosed;
    }

    @Override
    public void second() {


        for (Player player : playersInEvent) {


            if (player.getWorld().getName() != EventManager.eventWorldName) {
                EventManager.instance.kickPlayerFromEvent(player);
                player.sendRichMessage("<red>You have been removed from the event for leaving the event world.<red>");
            }


            if (startShowingBorder && borderCenter != null) {
                Vector3d playerPos = new Vector3d(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                double elapsedTime = System.currentTimeMillis() - eventStartTime;
                double totalDuration = 10 * 60 * 1000;
                double initialRadius = 100.0;
                double finalRadius = 5.0;
                double currentRadius = initialRadius - ((initialRadius - finalRadius) * (elapsedTime / totalDuration));
                currentRadius = Math.max(finalRadius, currentRadius);
                double distanceFromCenter = playerPos.distance(borderCenter);

                if (distanceFromCenter > currentRadius) {
                    double damage = 1.0 + ((distanceFromCenter - currentRadius) / 5.0);
                    player.damage(damage);
                    player.sendRichMessage("<red>You are outside the event border and taking damage! Move to the center.<red>");
                }
            }

        }

    }
}

