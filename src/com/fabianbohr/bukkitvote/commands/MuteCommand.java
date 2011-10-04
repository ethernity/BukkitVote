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
public class MuteCommand extends VoteCommand{

    Player toMute;
    MuteListener listener;
    public MuteCommand(Player toMute, BukkitVote bv, int pts, int mp, int vt) {
        super(null,pts,mp,vt);
        name="mute";
        this.toMute=toMute;
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
        return "/vote mute <name> - "+LocaleManager.getString("help.mute") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }
     public ChangeState makeHappen() {
         if(toMute!=null&&toMute.isOnline()) {
            listener.addPlayer(toMute.getName());
         }
         return new ChangeState(false,getInfo(),ChangeState.GENERAL_STATE);


     }


 public boolean equals(MuteCommand kc) {
        if(toMute.equals(kc.toMute))
            return true;
        else
            return false;
    }
    public boolean equals(Object o) {
        if(o instanceof MuteCommand)
            return equals((MuteCommand)o);
        else
            return false;

    }


}
