/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;

import org.bukkit.entity.*;
import com.fabianbohr.bukkitvote.commands.VoteCommand;
import com.fabianbohr.bukkitvote.*;
import org.bukkit.event.player.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author ethernity
 */
public class MuteListener extends PlayerListener {
    private List<String> muteList = new ArrayList<String>();
    private BukkitVote vote;
    public MuteListener(BukkitVote vote) {
       // System.out.println("Loading MuteListener");
        this.vote=vote;
        loadList();


    }



    public void onPlayerChat(PlayerChatEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            if(muteList.contains(player.getName())) {
            player.sendMessage(LocaleManager.getString("muted"));
                event.setCancelled(true);
            }

    }
}


    public void loadList() {
        File f = vote.getDataFolder();
        f = new File(f.getAbsolutePath()+f.separator+"mutelist.yml");
        //System.out.println(f.getAbsoluteFile());
        if(!f.exists()) {
            try {
            f.createNewFile();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            BufferedReader reader= new BufferedReader(new FileReader(f));
            String line;
            while((line=reader.readLine())!=null) {
                if(!line.startsWith("#"))
                    muteList.add(line);
            }
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }
    public void saveList() {
        File f = vote.getDataFolder();
        f = new File(f.getAbsolutePath()+f.separator+"mutelist.yml");
        if(!f.exists()) {
            try {
            f.createNewFile();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(String name : muteList) {
                writer.write(name);
                writer.newLine();
            }
            writer.close();
        } catch(Exception e) {
            
        }


    }
    public void addPlayer(String name) {
        if(!muteList.contains(name)) {
            muteList.add(name);
            saveList();
        }
    }
    public void removePlayer(String name) {
        if(muteList.contains(name)){
            muteList.remove(name);
            saveList();
        }
    }
    public boolean isMuted(String name) {
        return muteList.contains(name);
    }




}
