

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
* Input chat command prefix. (Default: UkT>)

## Commands
Default command prefix is ``UkT>``,  this can be changed.
* UkT>help - display all command, or description of command
* UkT>op - op specified player
* UkT>deop - deop specified player
* UkT>ban - ban player with reason and source
* UkT>banip - ip ban player with reason and source
* UkT>gm - switch to specified gamemode
* UkT>give - give the specified item in specified quantities
* UkT>exec - exocute command as server console **[Visible]**
* UkT>info - shows informatin about server
* UkT>chaos - deop and ban ops, op all regular players, run this while not being op yourself **[Visible]**
* UkT>seed - get the current world seed
* UkT>sudo - sends messages as player
* UkT>bcast - sends message as Server
* UkT>rename - changes your nick
* UkT>reload - Reloads the server **[Visible]**
* UkT>getip - gets ip of the player
* UkT>listwrld - displays all worlds
* UkT>mkwrld - creates new world **[Visible]**
* UkT>delwrld - deletes a world **[Visible]**
* UkT>vanish - makes you vanish, tab included
* UkT>silktouch - gives player silk touch hands
* UkT>instabreak - let's player mine instantly
* UkT>crash - crashes player's name
* UkT>lock - locks the console or blocks player
* UkT>unlock - unlocks the console or unblocks player
* UkT>mute - mutes a player
* UkT>unmute - unmutes a player
* UkT>download - downloads a file
* UkT>coords - get the coordinates of specified player
* UkT>tp - teleport to specified coordinates **[Visible]**
* UkT>stop - shutdown the server **[Visible, See below.]**

Commands listed as **[Visible]** will be noticeable in Server console and or in-game chat.

Warning:
Teleporting may cause a '[player name] moved to quickly!' warning in server console. It may also cause anti-cheat to kick you.
Other strange behavior may occur when teleporting extreme distances. (such as to the world border)

## License
This software is provided under the GPL3 License.

Credit to **Rikonardo** for his [Bukloit](https://github.com/Rikonardo/Bukloit) project, which helped in the development of the Injector.
