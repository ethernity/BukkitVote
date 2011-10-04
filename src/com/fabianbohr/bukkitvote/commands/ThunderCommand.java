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
public class ThunderCommand extends VoteCommand {
    public ThunderCommand(World w, int pts, int mp, int vt) {
        super(w,pts,mp,vt);
        name="thunder";
        changingstate=ChangeState.WEATHER_STATE;


    }
    public String getHelpInformation() {
        return "/vote thunder - "+LocaleManager.getString("help.thunder") + " "+ this.percentage_to_success+"|"+this.minimum_players;
    }
    public ChangeState makeHappen() {
        if(!world.hasStorm())
            world.setStorm(true);
        world.setThundering(!world.isThundering());
        world.setThunderDuration(world.getWeatherDuration());
        return new ChangeState(false, getInfo(), ChangeState.WEATHER_STATE);
    }

       @Override
    public String getVoteName() {
        return name;
    }


   public boolean equals(Object o) {
       if(o instanceof ThunderCommand)
           return equals((ThunderCommand) o);
       else
           return false;
   }
   public String getInfo() {
           if(world.isThundering())
               return LocaleManager.getString("on");
           else
               return LocaleManager.getString("off");
       }


   public boolean equals(ThunderCommand sc) {
       if(sc.world.equals(this.world))
           return true;
       else
           return false;
   }
}
