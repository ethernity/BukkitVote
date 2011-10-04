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
public class BanListener extends PlayerListener {
 
       private List<String> banList = new ArrayList<String>();
    private BukkitVote vote;

    public BanListener(BukkitVote vote) {
        this.vote = vote;
        loadList();
        //System.out.println("[BV] Loaded BanListener");
    }
 

    public void loadList() {
        banList = new ArrayList<String>();
        File f = vote.getDataFolder();
        f = new File(f.getAbsolutePath()+f.separator+"banlist.yml");
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
                    banList.add(line);
            }
        } catch(Exception e) {
            //e.printStackTrace();
        }
    }
    public void saveList() {
        File f = vote.getDataFolder();
        f = new File(f.getAbsolutePath()+f.separator+"banlist.yml");
        if(!f.exists()) {
            try {
            f.createNewFile();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(String name : banList) {
                writer.write(name);
                writer.newLine();
            }
            writer.close();
        } catch(Exception e) {
            
        }


    }
    public void addPlayer(String name) {
        if(!banList.contains(name)) {
            banList.add(name);
            saveList();
        }
    }
    public void removePlayer(String name) {
        if(banList.contains(name)){
            banList.remove(name);
            saveList();
        }
    }
    public boolean isBanned(String name) {
        return banList.contains(name);
    }

     
     
     
     public void onPlayerLogin(PlayerLoginEvent event) {
        // System.out.println("[BV]Entered  Login Event");
         loadList();
     if(banList.contains(event.getPlayer().getName())) {
         //System.out.println("[BV] Banning " +event.getPlayer().getName());
         event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Vote Ban");
        // event.getPlayer().kickPlayer("Vote Banned");
     }
     }
     

}
