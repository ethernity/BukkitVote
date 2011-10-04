/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;
import org.bukkit.*;
import com.fabianbohr.bukkitvote.*;
/**
 *
 * @author ethernity
 */
public class EternalNightCommand extends VoteCommand {
    TimeSetter ts;
   
    public EternalNightCommand(World w,int pts, int mp,int vt) {
        super(w,pts,mp,vt);
        name="eternalnight";
        changingstate = ChangeState.TIME_STATE;
    }

    public ChangeState makeHappen() {
            ts = new TimeSetter(world,20*1000,TimeSetter.eternalnight);
            Thread t = new Thread(ts);
               t.start();
            return new ChangeState(true,getInfo(),ChangeState.TIME_STATE);
    }
    public String getVoteName() {
        return name;
    }


    public String getHelpInformation() {
        return "/vote eternalnight - "+LocaleManager.getString("help.eternalnight") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }
    public void deactivate() {
        ts.deactivate();
    }

    public String getInfo() {
               String s= "";
         if(ts.activated)
            s+=LocaleManager.getString("activated");
        else
            s+=LocaleManager.getString("deactivated");
        return s;
    }



}
