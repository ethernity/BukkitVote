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
public class TimeChangeCommand extends VoteCommand {


    boolean changeToDay;
   
    public TimeChangeCommand(boolean day, World w, int pts, int mp,int vt) {
    super(w,pts,mp,vt);
    this.name="TimeChange";
    
    changeToDay=day;
    changingstate = ChangeState.TIME_STATE;


    }


    public ChangeState makeHappen() {
        if(changeToDay) {
            world.setTime(0);
        } else {
            world.setTime(13*1000);
        }
                    return new ChangeState(false,getInfo(),ChangeState.TIME_STATE);


    }
    public String getVoteName() {
        if(changeToDay) {
            return "day";
        } else
            return "night";
    }


    @Override
    public String getPermissionNodeName() {
        return getVoteName();
    }


    public String getHelpInformation() {
        if(changeToDay)
            return "/vote day - "+LocaleManager.getString("help.day") + " "+ this.percentage_to_success+"|"+this.minimum_players;
        else
            return "/vote night - "+LocaleManager.getString("help.night") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }



}
