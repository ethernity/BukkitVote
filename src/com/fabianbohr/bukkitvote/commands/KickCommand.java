/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;
import org.bukkit.entity.*;
import com.fabianbohr.bukkitvote.*;
/**
 *
 * @author ethernity
 */
public class KickCommand extends VoteCommand {
    Player player;
    public KickCommand(Player p, int pts, int mp, int vt) {
        super(null, pts,mp,vt);
        name="kick";
        player=p;
        changingstate = ChangeState.GENERAL_STATE;
        isGlobal=true;
    }

    public String getVoteName() {
        if(player==null)
        return name;
        else
            return name+" "+player.getName();
    }
    public ChangeState makeHappen() {
        if(player!=null&&player.isOnline())
        player.kickPlayer("Vote kick");
        

            return new ChangeState(false,getInfo(),ChangeState.GENERAL_STATE);


    }

    public String getHelpInformation() {
        return "/vote kick <name> - "+LocaleManager.getString("help.kick") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }


    public boolean equals(KickCommand kc) {
        if(player.equals(kc.player))
            return true;
        else
            return false;
    }
    public boolean equals(Object o) {
        if(o instanceof KickCommand)
            return equals((KickCommand)o);
        else
            return false;

    }







}
