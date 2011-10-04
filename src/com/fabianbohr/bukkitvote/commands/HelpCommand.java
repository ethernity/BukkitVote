/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;
import com.fabianbohr.bukkitvote.VoteCommandFabric;
import com.fabianbohr.bukkitvote.*;
import org.bukkit.entity.*;
/**
 *
 * @author ethernity
 */
public class HelpCommand extends VoteCommand {

    Player p;
    BukkitVote bv;
    public HelpCommand(Player p,BukkitVote bv) {
        super(null,-1,-1,-1);
        this.p=p;
        this.bv=bv;
        changingstate = ChangeState.GENERAL_STATE;
        needsVote=false;
        isGlobal=true;
    }


    public ChangeState makeHappen() {
        p.sendMessage(org.bukkit.ChatColor.GOLD+"BukkitVote "+ BukkitVote.version);
        String[] ar = VoteCommandFabric.getInstance().getAllVoteCommands();
        for(String s:ar) {
            String[] sa = {s};
            p.sendMessage(org.bukkit.ChatColor.YELLOW+VoteCommandFabric.getInstance().getVoteCommand(sa, bv, null).getHelpInformation());
            
        }
         return new ChangeState(false,getInfo(),ChangeState.GENERAL_STATE);

    }

    public String getVoteName() {
        return "help";
    }

    public String getHelpInformation() {
        return "/vote help - "+LocaleManager.getString("help.help");
                //+ " "+ this.percentage_to_success+"|"+this.minimum_players;
    }

}
