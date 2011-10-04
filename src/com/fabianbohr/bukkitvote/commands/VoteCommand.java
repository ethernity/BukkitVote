/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote.commands;


import org.bukkit.*;
import java.util.*;

/**
 *
 * @author ethernity
 */
public abstract class VoteCommand {

    String name;
   public int changingstate;
    List<String[]> args = new ArrayList<String[]>();
    boolean isGlobal = false;
    boolean needsVote = true;
    int percentage_to_success = -1;
    int minimum_players = -1;
    World world = null;
    long time;
    public int voteTime;
    public VoteCommand(World w, int pts, int mp,int vt) {
        world = w;
        percentage_to_success=pts;
        minimum_players=mp;
        time = System.currentTimeMillis();
        voteTime=vt;
    }

    public abstract ChangeState makeHappen();

    public String getName() {
        return name;
    }
    
    
    
    public boolean isGlobal() {
        return isGlobal;
    }
    public boolean needsVote() {
        return needsVote;
    }
    

    public abstract String getVoteName();

    public void addArguments(String[] arg) {
        args.add(arg);
    }

    public boolean equals(VoteCommand c) {
        if (getVoteName().equals(c.getVoteName())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Object o) {
        VoteCommand c = o instanceof VoteCommand ? (VoteCommand) o : null;
        if (c != null) {
            return equals(c);
        } else {
            return false;
        }
    }

    public void deactivate() {
    }
    public String getInfo() {
        return "";
    }

    public abstract String getHelpInformation();

    

    public World getWorld() {
        return world;
    }

    public int getState() {
        return changingstate;
    }

    public int getMinimum_players() {
        return minimum_players;
    }

    public int getPercentage_to_success() {
        return percentage_to_success;
    }
    public String getPermissionNodeName() {
        return name;
    }
    
    public boolean isDue() {
        if(voteTime==0)
            return false;
       else if(System.currentTimeMillis()-time>=voteTime*1000) {
            return true;            
        } else {
            return false;
        }
    }
    
}
