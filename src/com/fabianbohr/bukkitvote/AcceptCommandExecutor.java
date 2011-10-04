/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote;
import com.fabianbohr.bukkitvote.commands.*;
import org.bukkit.event.player.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
/**
 *
 * @author ethernity
 */
public class AcceptCommandExecutor implements CommandExecutor {


     VoteListener global;
    BukkitVote bvote;

    public AcceptCommandExecutor(VoteListener g, BukkitVote bv) {
        global = g;
        bvote = bv;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        World world = sender instanceof Player ? ((Player) sender).getWorld() : null;
        //System.out.println(args[0]);
        if (world == null) {
            System.out.println("Error at casting vote [0]");
            return false;
        } else {
            Player p = (Player) sender;


            VoteCommand vcommand = VoteCommandFabric.getInstance().getVoteCommand(args, bvote, p);
            if (hasPermissions(p)) {
            if (vcommand != null) {
                for (Player pl : bvote.getServer().getOnlinePlayers()) {
                    List<VoteCommand> list = global.votelist.get(pl);
                if (list != null) {
                if (list.contains(vcommand)&&!pl.equals(p)) {
                    global.addVote(p, vcommand);
                    return true;
                    }
                    }
                }
                p.sendMessage(LocaleManager.getString("error.accept.already"));
                return true;
            } else {
                 p.sendMessage(LocaleManager.getString("error.accept.unknown"));

            }
            } 


        }
        return true;
    }



    public boolean hasPermissions(Player p) {
        if (BukkitVote.Permissions != null && p != null) {
            if (BukkitVote.Permissions.has(p, "bukkitvote.accept")) {
                return true;
            } else {
                p.sendMessage(LocaleManager.getString("error.permissions"));
                return false;
            }
        } else {
            return true;
        }

    }



}
