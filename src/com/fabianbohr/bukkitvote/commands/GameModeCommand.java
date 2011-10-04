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
public class GameModeCommand extends VoteCommand {

    Server serv;

    public GameModeCommand(Server serv, int pts, int mp, int vt) {
        super(null, pts, mp, vt);
        name = "switchmode";
        this.serv = serv;
        changingstate = ChangeState.GENERAL_STATE;
        isGlobal = true;
    }

    public String getVoteName() {
        return name;
    }

    public ChangeState makeHappen() {
        GameMode mode = serv.getDefaultGameMode();
        if (mode.equals(GameMode.CREATIVE)) {
            //System.out.println("[BV] From creative to survival");
            serv.setDefaultGameMode(GameMode.SURVIVAL);
            setAllOnline(GameMode.SURVIVAL);
        } else if (mode.equals(GameMode.SURVIVAL)) {
            //System.out.println("[BV] From survival to creative");
            serv.setDefaultGameMode(GameMode.CREATIVE);
            setAllOnline(GameMode.CREATIVE);
        }
        return new ChangeState(false, getInfo(), ChangeState.GENERAL_STATE);


    }

    private void setAllOnline(GameMode mode) {
        for (Player p : serv.getOnlinePlayers()) {
            p.setGameMode(mode);
        }
    }

    public String getHelpInformation() {
        return "/vote switchmode <name> -" + LocaleManager.getString("help.switchmode") + " " + this.percentage_to_success + "|" + this.minimum_players;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GameModeCommand) {
            return true;
        } else {
            return false;
        }
    }
}
