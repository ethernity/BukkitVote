/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;
import org.bukkit.*;
import com.fabianbohr.bukkitvote.*;
import org.bukkit.entity.*;
/**
 *
 * @author ethernity
 */
public class WorldInfoCommand extends VoteCommand {
    VoteListener vl;
    Player p;
    
    public WorldInfoCommand(VoteListener listener, Player p) {
        super(p!=null?p.getWorld():null,-1,-1,-1);
        name = "worldinfo";
        vl = listener;
        this.p = p;
        changingstate = ChangeState.GENERAL_STATE;
        needsVote=false;

    }

    @Override
    public String getHelpInformation() {
        return "/vote worldinfo - displays information of the world the player is in";
    }

    @Override
    public ChangeState makeHappen() {
        p.sendMessage(ChatColor.GOLD+vl.getInfo(world));
        World w = p.getWorld();
        if(w.hasStorm()) {
            p.sendMessage(ChatColor.GOLD+"Storm duration: "+w.getWeatherDuration());
        }
        if(w.isThundering())
            p.sendMessage(ChatColor.GOLD+"Thunder duration: "+w.getThunderDuration());

        return new ChangeState(false,getInfo(),ChangeState.GENERAL_STATE);
    }

    @Override
    public String getVoteName() {
        return name;
    }

    




}
