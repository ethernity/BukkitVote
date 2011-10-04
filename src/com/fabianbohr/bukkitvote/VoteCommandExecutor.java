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
public class VoteCommandExecutor implements CommandExecutor {

    VoteListener global;
    BukkitVote bvote;

    public VoteCommandExecutor(VoteListener g, BukkitVote bv) {
        global = g;
        bvote = bv;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        World world = sender instanceof Player ? ((Player) sender).getWorld() : null;
        //System.out.println(args[0]);
        if (world == null) {
            System.out.println("Error at casting vote [0]");
            return false;
        } else if (args == null || args.length == 0) {
            Player p = (Player) sender;

            String[] sa = new String[1];
            sa[0] = "help";
            VoteCommand vcommand = VoteCommandFabric.getInstance().getVoteCommand(sa, bvote, p);
            global.addVote(p, vcommand);
            return true;


        } else {
            Player p = (Player) sender;

            if (VoteCommandFabric.getInstance().hasPermissions(p, args[0])) {
                VoteCommand vcommand = VoteCommandFabric.getInstance().getVoteCommand(args, bvote, p);
                if (vcommand != null) {

                    global.addVote(p, vcommand);
                    return true;


                }



            }
            return true;
        }
    }
}
    
