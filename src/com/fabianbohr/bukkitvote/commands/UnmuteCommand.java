/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;

import com.fabianbohr.bukkitvote.BukkitVote;
import com.fabianbohr.bukkitvote.commands.ChangeState;
import com.fabianbohr.bukkitvote.commands.VoteCommand;
import com.fabianbohr.bukkitvote.*;
import org.bukkit.entity.*;
/**
 *
 * @author ethernity
 */
public class UnmuteCommand extends VoteCommand{
    Player toUnmute;
    MuteListener listener;
    public UnmuteCommand(Player toMute, BukkitVote bv, int pts, int mp, int vt) {
        super(null,pts,mp,vt);
        name="unmute";
        this.toUnmute=toMute;
        changingstate = ChangeState.GENERAL_STATE;
        isGlobal=true;
        if(bv!=null) {
            listener=bv.getMuteListener();
        } else {
            listener = null;
        }
    }
    public String getVoteName() {
        return name;
    }
     public String getHelpInformation() {
        return "/vote unmute <name> - "+LocaleManager.getString("help.unmute") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }
     public ChangeState makeHappen() {
         if(toUnmute!=null) {
            listener.removePlayer(toUnmute.getName());
         }
         return new ChangeState(false,getInfo(),ChangeState.GENERAL_STATE);


     }


 public boolean equals(UnmuteCommand kc) {
        if(toUnmute.equals(kc.toUnmute))
            return true;
        else
            return false;
    }
    public boolean equals(Object o) {
        if(o instanceof UnmuteCommand)
            return equals((UnmuteCommand)o);
        else
            return false;

    }


}
