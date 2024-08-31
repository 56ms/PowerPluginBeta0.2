package me.power16.powerplugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.power16.powerplugin.commands.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import me.power16.powerplugin.economy.EconomyManager;
import me.power16.powerplugin.listeners.*;
import me.power16.powerplugin.ranks.RankManager;
import me.power16.powerplugin.tasks.RainbowCycleTask;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public final class PowerPlugin extends JavaPlugin {

    private static PowerPlugin instance;
    private EconomyManager economyManager;
    private RankManager rankManager;
    private PlayerStatsManager playerStatsManager;
    private ItemCommand itemCommand;
    private final Map<String, World> worldMap = new HashMap<>();
    private final Map<Player, Scoreboard> playerScoreboards = new HashMap<>();
    private final int defaultMaxMana = 100;



    @Override
    public void onEnable() {


        // Initialize plugin instance
        instance = this;
        economyManager = new EconomyManager(getDataFolder());
        rankManager = new RankManager(this, getDataFolder());
        playerStatsManager = new PlayerStatsManager();
        itemCommand = new ItemCommand(this, rankManager);

        // Initialize worlds
        initializeWorlds();

        // Register event listeners

        getServer().getPluginManager().registerEvents(new ChatListener(rankManager), this);
        getServer().getPluginManager().registerEvents(new CustomItemsGUIListener(this, rankManager), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new ItemInteractListener(this, itemCommand, economyManager), this);
        getServer().getPluginManager().registerEvents(new MenuClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMenuListener(), this);
        getServer().getPluginManager().registerEvents(new TrampleListener(), this);
        getServer().getPluginManager().registerEvents(new BreakBlockListener(), this);

        // Register commands
        registerCommand("amount", new SetAmountCommand());
        registerCommand("armorcolor", new ArmorColorCommand());
        registerCommand("balance", new BalanceCommand(this));
        registerCommand("broadcast", new BroadcastCommand());
        registerCommand("ci", new ItemCommand(this, rankManager));
        registerCommand("clearspawnpoint", new ClearSpawnPointCommand(this));
        registerCommand("clearworldspawn", new ClearWorldSpawnCommand(this));
        registerCommand("day", new DayCommand());
        registerCommand("die", new DieCommand());
        registerCommand("dupe", new DupeCommand());
        registerCommand("enderchest", new EnderChestCommand());
        registerCommand("gmc", new GMCCommand());
        registerCommand("god", new GodCommand());
        registerCommand("hat", new HatCommand());
        registerCommand("help", new PowerPluginHelpCommand(this));
        registerCommand("invsee", new INVSeeCommand());
        registerCommand("lore", new LoreCommand());
        registerCommand("meow", new MeowCommand(this));
        registerCommand("money", new MoneyCommand(this));
        registerCommand("mypermissions", new GetPermissionsCommand());
        registerCommand("nbt", new NBTCommand(this));
        registerCommand("nick", new NickCommand());
        registerCommand("night", new NightCommand());
        registerCommand("pay", new PayCommand(this));
        registerCommand("rarity", new RarityCommand());
        registerCommand("rename", new ItemRenameCommand());
        registerCommand("repeat", new RepeatCommand());
        registerCommand("repeatcommand", new RepeatCommandExecutor(this));
        registerCommand("servermessage", new ServerCommand());
        registerCommand("setitem", new SetItemCommand());
        registerCommand("setrank", new SetRankCommand(rankManager));
        registerCommand("setserverspawn", new SetServerSpawnCommand(this));
        registerCommand("trash", new TrashCommand());
        registerCommand("warpmenu", new OpenMenuCommand(this, rankManager));
        registerCommand("world", new WorldCommand(this));
        registerCommand("ppspawn", new PPSCommand());
        registerCommand("randommove", new MoveCommand());
        registerCommand("setintel", new ItemManaCommand(this));

        // Register tab completers
        registerTabCompleter("ci", itemCommand);
        registerTabCompleter("nbt", new NBTCommand(this));
        registerTabCompleter("rarity", new RarityCommand());
        registerTabCompleter("world", new WorldCommand(this));
        registerTabCompleter("ppspawn", new PPSCommand());

        // Start repeating tasks
        startTasks();

        getLogger().info("Power Plugin ONLINE!!!");
    }

    private void registerCommand(String name, Object executor) {
        PluginCommand command = getCommand(name);
        if (command != null) {
            command.setExecutor((CommandExecutor) executor);
        } else {
            getLogger().warning("Command '" + name + "' not found in plugin.yml");
        }
    }

    private void registerTabCompleter(String name, Object tabCompleter) {
        PluginCommand command = getCommand(name);
        if (command != null) {
            command.setTabCompleter((TabCompleter) tabCompleter);
        } else {
            getLogger().warning("Command '" + name + "' not found in plugin.yml");
        }
    }

    private void startTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ItemStack helmet = player.getInventory().getHelmet();
                    if (helmet != null && helmet.getType().name().contains("STAINED_GLASS")) {
                        new RainbowCycleTask(player, PowerPlugin.this).runTaskTimer(PowerPlugin.this, 0L, 4L);
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                updateAllMobsHealth(1);
            }
        }.runTaskTimer(this, 0L, 20L);

        new BukkitRunnable() {
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    // Update player stats
                    PowerPlugin.this.playerStatsManager.updatePlayerStats(player);
                    PlayerStatsManager.PlayerStats stats = PowerPlugin.this.playerStatsManager.getPlayerStats(player);
                    int currentMana = stats.getMana();
                    int maxMana = stats.getMaxMana();
                    currentMana = (int) ((double) currentMana + (double) maxMana * 0.05);
                    stats.setMana(Math.min(currentMana, maxMana));

                    // Initialize or update scoreboards and objectives
                    Scoreboard scoreboard = PowerPlugin.this.playerScoreboards.get(player);
                    if (scoreboard == null) {
                        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                        Objective healthObjective = scoreboard.registerNewObjective("health", "dummy", ChatColor.RED + "Custom Health");
                        healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                        Objective defenseObjective = scoreboard.registerNewObjective("defense", "dummy", ChatColor.GREEN + "Defense");
                        Objective pingObjective = scoreboard.registerNewObjective("ping", "dummy", ChatColor.RED + "Ping");
                        pingObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
                        player.setScoreboard(scoreboard);
                        PowerPlugin.this.playerScoreboards.put(player, scoreboard);
                    }

                    // Update existing objectives
                    Objective healthObjective = scoreboard.getObjective("health");
                    Objective defenseObjective = scoreboard.getObjective("defense");
                    Objective pingObjective = scoreboard.getObjective("ping");

                    int customHealth = stats.getHealth();
                    int defense = stats.getDefense();
                    healthObjective.getScore(player.getName()).setScore(customHealth);
                    defenseObjective.getScore(player.getName()).setScore(defense);
                    pingObjective.getScore(player.getName()).setScore(player.getPing());

                    // Update sidebar content separately
                    updateSidebar(player);

                    // Send action bar message
                    String actionBarMessage = String.format("%sHealth: %d     %sDefense: %d     %sMana: %d/%d",
                            ChatColor.RED, customHealth, ChatColor.GREEN, defense, ChatColor.AQUA, stats.getMana(), stats.getMaxMana());

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionBarMessage));
                }
            }
        }.runTaskTimer(this, 0L, 10L);
    }


    public void updateSidebar(Player player) {
        // Ensure the player has a scoreboard
        if (player.getScoreboard() == null) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        // Get the player's scoreboard
        Scoreboard scoreboard = player.getScoreboard();

        // Get or create the sidebar objective
        Objective sidebarObjective = scoreboard.getObjective("sidebar");
        if (sidebarObjective == null) {
            // Register a new objective if it doesn't exist
            sidebarObjective = scoreboard.registerNewObjective("sidebar", "dummy", "Your Display Name");
            sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        } else {
            // If it exists, update its display name
            sidebarObjective.setDisplayName(ChatColor.LIGHT_PURPLE.toString() +ChatColor.BOLD.toString()+  "SKYBLOCK");
        }

        // Clear all previous scores to avoid conflicts
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        String formattedDate = currentDate.format(formatter);

        long time = player.getWorld().getTime(); // Time in ticks
        int hours = (int)((time / 1000 + 6) % 24); // Convert to hour of day (0-23), with offset
        int minutes = (int)((time % 1000) * 60 / 1000); // Convert remaining ticks to minutes
        minutes = (minutes / 10) * 10; // Round down to the nearest multiple of 10
        String amPm = (hours >= 12) ? "PM" : "AM";
        if (hours > 12) {
            hours -= 12;
        } else if (hours == 0) {
            hours = 12; // Midnight case
        }

// Format time as 12-hour clock with AM/PM
        String timeString = String.format("%02d:%02d %s", hours, minutes, amPm);

        sidebarObjective.getScore(ChatColor.GRAY + formattedDate).setScore(10);

// Use different space strings for each blank line
        sidebarObjective.getScore(ChatColor.RESET + " ").setScore(9);
        sidebarObjective.getScore(ChatColor.GRAY + timeString).setScore(8);
        sidebarObjective.getScore(ChatColor.GREEN + "!LOCATION(CS)!").setScore(7);

// Another unique space string for the second blank line
        sidebarObjective.getScore(ChatColor.RESET + "  ").setScore(6);
        sidebarObjective.getScore(ChatColor.GOLD + "Piggy: " + economyManager.formatBalance(PowerPlugin.this.economyManager.getBalance(player.getUniqueId()))).setScore(5);

// Another unique space string for the third blank line
        sidebarObjective.getScore(ChatColor.RESET + "   ").setScore(4);
        sidebarObjective.getScore(ChatColor.WHITE + "Objective").setScore(3);
        sidebarObjective.getScore(ChatColor.YELLOW.toString() + ChatColor.BOLD + "COMING SOON").setScore(2);
        sidebarObjective.getScore(ChatColor.RESET + "  ").setScore(1);
        sidebarObjective.getScore(ChatColor.GOLD.toString() + ChatColor.BOLD + "PowerPlugin Server").setScore(0);


        player.setScoreboard(scoreboard);
    }

//     sidebarObjective.getScore("<Date: MM/DD/YY>").setScore(9);
//     sidebarObjective.getScore("").setScore(8);
//     sidebarObjective.getScore(ChatColor.RED + "COMING SOON").setScore(7);
//     sidebarObjective.getScore("Minecraft: World Time").setScore(6);
//     sidebarObjective.getScore(ChatColor.GREEN + "LOCATION COMING SOON").setScore(5);
//     sidebarObjective.getScore("").setScore(4);
//     sidebarObjective.getScore("Purse: " + PowerPlugin.this.economyManager.getBalance(player.getUniqueId())).setScore(3);
//     sidebarObjective.getScore("").setScore(2);
//     sidebarObjective.getScore(ChatColor.WHITE + "Objective").setScore(1);
//     sidebarObjective.getScore(ChatColor.YELLOW + "Craft a workbench").setScore(0);




    public void updateAllMobsHealth(int level) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World world : Bukkit.getWorlds()) {
                    World hubWorld = Bukkit.getWorld(world.getName());
                    if (hubWorld != null) {
                        Iterator<Entity> entities = hubWorld.getEntities().iterator();

                        while (entities.hasNext()) {
                            Entity entity = entities.next();
                            if (entity instanceof Mob) {
                                Mob mob = (Mob) entity;
                                String mobName = mob.getCustomName();
                                double maxHealth = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                                String healthStatus = mob.isDead() ? "Dead" : (int) mob.getHealth() + "/" + (int) maxHealth;
                                if (mobName != null && mobName.contains("Crypt Ghoul")) {
                                    mob.setCustomName(ChatColor.GRAY + "[lvl 5] " + ChatColor.RED + "Crypt Ghoul " + healthStatus);
                                } else {
                                    mob.setCustomName(ChatColor.GRAY + "[lvl 1] " + ChatColor.RED + mob.getType().name() + " " + healthStatus);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }


    public static PowerPlugin getInstance() {
        return instance;
    }

    public PlayerStatsManager getPlayerStatsManager() {
        return playerStatsManager;
    }

    private void initializeWorlds() {
        // Always initialize the "Hub" world
        String hubWorldName = "Hub";
        World hubWorld = Bukkit.getWorld(hubWorldName);
        if (hubWorld == null) {
            WorldCreator worldCreator = new WorldCreator(hubWorldName);
            Bukkit.createWorld(worldCreator);
            getLogger().info("Created world: " + hubWorldName);
        } else {
            getLogger().info("World already exists: " + hubWorldName);
        }

        // Initialize other worlds
        for (World world : Bukkit.getWorlds()) {
            worldMap.put(world.getName(), world);
        }
    }


    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
