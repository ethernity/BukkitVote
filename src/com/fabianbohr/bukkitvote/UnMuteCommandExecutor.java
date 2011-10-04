/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;

import com.fabianbohr.bukkitvote.MuteListener;
import com.fabianbohr.bukkitvote.commands.*;
import org.bukkit.event.player.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.fabianbohr.bukkitvote.*;

/**
 *
 * @author ethernity
 */
public class UnMuteCommandExecutor implements CommandExecutor {

    MuteListener listener;

    public UnMuteCommandExecutor(MuteListener listener) {
        this.listener = listener;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            if (BukkitVote.Permissions != null) {
               
                if (BukkitVote.Permissions.has((Player) sender, "bukkitvote.op.unmute" + command)) {
                    if (listener.isMuted(args[0])) {
                        listener.removePlayer(args[0]);
                        sender.sendMessage(LocaleManager.getString("mute.broadcast", LocaleManager.replacePlayer(args[0])));
                    } else {
                        sender.sendMessage(LocaleManager.getString("mute.notmuted", LocaleManager.replacePlayer(args[0])));
                    }
                } else {
                    sender.sendMessage(LocaleManager.getString("error.permissions"));
                    
                }


            } else {
                if (sender.isOp() && listener.isMuted(args[0])) {
                    listener.removePlayer(args[0]);
                    sender.sendMessage(LocaleManager.getString("mute.broadcast", LocaleManager.replacePlayer(args[0])));
                } else {
                    sender.sendMessage(LocaleManager.getString("mute.notmuted", LocaleManager.replacePlayer(args[0])));
                }
            }

        }


        return true;
    }
}
