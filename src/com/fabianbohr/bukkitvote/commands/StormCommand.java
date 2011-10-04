/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.fabianbohr.bukkitvote.*;
/**
 *
 * @author ethernity
 */
public class StormCommand extends VoteCommand {



    public StormCommand(World w, int pts, int mp, int vt) {
        super(w,pts,mp,vt);
        name="storm";
        changingstate = ChangeState.WEATHER_STATE;


    }
    public String getHelpInformation() {
        return "/vote storm - "+LocaleManager.getString("help.storm") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }
    public ChangeState makeHappen() {
        world.setStorm(!world.hasStorm());
        return new ChangeState(false, getInfo(), ChangeState.WEATHER_STATE);
    }

       @Override
    public String getVoteName() {
           return name;
    }
       public String getInfo() {
           if(world.hasStorm())
               return LocaleManager.getString("on");
           else
               return LocaleManager.getString("off");
       }


   public boolean equals(Object o) {
       if(o instanceof StormCommand)
           return equals((StormCommand) o);
       else
           return false;
   }
   public boolean equals(StormCommand sc) {
       if(sc.world.equals(this.world))
           return true;
       else
           return false;
   }

}
