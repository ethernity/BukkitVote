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
public class ForceVoteCommand implements CommandExecutor {

    VoteListener global;
    BukkitVote bvote;
    public static String commandName = "fvote";

    public ForceVoteCommand(VoteListener g, BukkitVote bv) {
        global = g;
        bvote = bv;
    }

    private boolean hasPermissions(Player p) {
        if (BukkitVote.Permissions != null && p != null) {
            if (BukkitVote.Permissions.has(p, "bukkitvote.op." + commandName)) {
                return true;
            } else {
                p.sendMessage(LocaleManager.getString("error.force.permissions"));
                return false;
            }
        } else if (p != null && p.isOp()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? ((Player) sender) : null;
        if (player != null) {
            if (hasPermissions(player)) {
                if (args.length > 1 && args[0].equals("strike")) {
                    Player toStrike = bvote.getServer().getPlayer(args[1]);
                    if (toStrike != null && toStrike.isOnline()) {
                        toStrike.getWorld().strikeLightning(toStrike.getLocation());
                        return true;
                    }
                }
                VoteCommandFabric fabric = VoteCommandFabric.getInstance();
                VoteCommand vc = fabric.getVoteCommand(args, bvote, player);

                List<VoteCommand> last = global.last;
                if (vc != null) {
                    if (last.size() > 0) {
                        for (int j = 0; j < last.size(); j++) {
                            VoteCommand vl = last.get(j);
                            // System.out.println(state + "   " + vc.getState());
                            if (vc.getState() == vl.getState()) {
                                vl.deactivate();
                                last.remove(vl);
                            }
                        }

                    }
                    if (global.votelist != null) {
                        Player[] onlinePlayers = bvote.getServer().getOnlinePlayers();
                        for (Player p : onlinePlayers) {
                            List<VoteCommand> list = global.votelist.get(p);
                            if (list != null) {
                                if (list.contains(vc)) {
                                    list.remove(vc);
                                }
                            }

                        }
                    }
                    ChangeState whathappened=vc.makeHappen();
                    if (whathappened.hasPermanentChange())
                        global.last.add(vc);
                    bvote.broadcastMessage(LocaleManager.getString("force.success"));
                    return true;
                }

            }
        }



        return false;
    }
}
