/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;
import org.bukkit.entity.*;
import com.fabianbohr.bukkitvote.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.*;
/**
 *
 * @author ethernity
 */
public class BanCommand extends VoteCommand {
    Player player;
    BukkitVote bv;   
    BanListener listener;

    public BanCommand(Player p, BukkitVote bv, int pts, int mp,int vt) {
        super(null,pts,mp,vt);
        name="ban";
        player=p;
        this.bv=bv;
        changingstate = ChangeState.GENERAL_STATE;
        isGlobal=true;
         if(bv!=null) {
            listener=bv.getBanListener();
        } else {
            listener = null;
        }
    }

    public String getVoteName() {
        return name;
    }
    public ChangeState makeHappen() {
        if(player!=null&&player.isOnline()) {
        player.kickPlayer("Vote Ban");
        listener.addPlayer(player.getName());
    
        }    

            return new ChangeState(false,getInfo(),ChangeState.GENERAL_STATE);


    }

    public String getHelpInformation() {
        return "/vote ban <name> -"+LocaleManager.getString("help.ban") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }


    public boolean equals(BanCommand kc) {
        if(player.equals(kc.player))
            return true;
        else
            return false;
    }
    public boolean equals(Object o) {
        if(o instanceof BanCommand)
            return equals((BanCommand)o);
        else
            return false;

    }







}
