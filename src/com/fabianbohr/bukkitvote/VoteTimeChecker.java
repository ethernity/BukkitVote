/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;

import org.bukkit.entity.*;
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
public class VoteTimeChecker implements Runnable {

    VoteListener listener;
    BukkitVote vote;

    public VoteTimeChecker(VoteListener ls, BukkitVote vote) {
        listener = ls;
        this.vote = vote;
    }

    public void run() {
        while (true) {
            try {

                if (listener.locked) {
                    Thread.sleep(10);
                }
                ArrayList<VoteCommand> removed = new ArrayList<VoteCommand>();
                Set<Player> players = listener.votelist.keySet();
                for (Player p : players) {
                    List<VoteCommand> comms = listener.votelist.get(p);
                    for (VoteCommand c : comms) {
                        if (c.isDue() || removed.contains(c)) {
                            comms.remove(c);
                        
                        if (!removed.contains(c)) {
                            removed.add(c);
                            HashMap<String,String> formatMap = new HashMap<String,String>();
                            formatMap.put("VOTENAME", c.getVoteName());
                            vote.broadcastMessage(LocaleManager.getString("error.vote.timelimit", formatMap));
                        }
                        }
                    }
                }
                Thread.sleep(1000);

            } catch (Exception e) {
            }

        }

    }
}