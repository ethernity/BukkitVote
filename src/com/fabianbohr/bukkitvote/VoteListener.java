/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;

import org.bukkit.entity.*;
import com.fabianbohr.bukkitvote.commands.VoteCommand;
import org.bukkit.event.player.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.util.*;


import com.fabianbohr.bukkitvote.commands.*;

/**
 *
 * @author ethernity
 */
public class VoteListener extends org.bukkit.event.player.PlayerListener {
    HashMap<Player, List<VoteCommand>> votelist;
    BukkitVote bv;
    List<VoteCommand> last;
    public boolean locked=false;
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")
    public VoteListener(BukkitVote bv) {
        this.bv = bv;
        votelist = new HashMap<Player, List<VoteCommand>>();
        last = new ArrayList<VoteCommand>();
        VoteTimeChecker checker = new VoteTimeChecker(this,bv);
        Thread t = new Thread(checker);
        t.start();
    }

    public void addVote(Player p, VoteCommand command) {
        //System.out.println(votelist.toString());
        locked=true;
        if (command != null) {
            HashMap<String,String> replaceMap = this.voteMap(p.getDisplayName(), command.getVoteName(), ""+command.getPercentage_to_success(), ""+command.voteTime);
            if (command.needsVote()) {
                Player[] onlinePlayers;
                if (command.getWorld() != null) {
                    onlinePlayers = command.getWorld().getPlayers().toArray(new Player[0]);
                } else {
                    onlinePlayers = bv.getServer().getOnlinePlayers();
                }
                if (getCountRealPlayers(onlinePlayers,command.getPermissionNodeName()) >= command.getMinimum_players()) {
                    if (votelist.containsKey(p)) {
                        if (!votelist.get(p).contains(command)) {
                            votelist.get(p).add(command);
                            if(command.isGlobal()) {
                            bv.broadcastMessage(LocaleManager.getString("vote.start",replaceMap));
                            bv.broadcastMessage(LocaleManager.getString("vote.cond",replaceMap));
                            if(command.voteTime>0)
                            bv.broadcastMessage(LocaleManager.getString("vote.cond2",replaceMap));
                            } else {
                            bv.worldBroadcast(LocaleManager.getString("vote.start",replaceMap), command.getWorld());
                            bv.worldBroadcast(LocaleManager.getString("vote.cond",replaceMap), command.getWorld());
                            if(command.voteTime>0)
                            bv.worldBroadcast(LocaleManager.getString("vote.cond2",replaceMap), command.getWorld());
                            }
                         }
                           
                    } else {
                        List<VoteCommand> temp = new ArrayList<VoteCommand>();
                        temp.add(command);
                        votelist.put(p, temp);
                         if(command.isGlobal()) {
                            bv.broadcastMessage(LocaleManager.getString("vote.start",replaceMap));
                            bv.broadcastMessage(LocaleManager.getString("vote.cond",replaceMap));
                            if(command.voteTime>0)
                            bv.broadcastMessage(LocaleManager.getString("vote.cond2",replaceMap));
                            } else {
                            bv.worldBroadcast(LocaleManager.getString("vote.start",replaceMap), command.getWorld());
                            bv.worldBroadcast(LocaleManager.getString("vote.cond",replaceMap), command.getWorld());
                            if(command.voteTime>0)
                            bv.worldBroadcast(LocaleManager.getString("vote.cond2",replaceMap), command.getWorld());
                            }
                    }
                    evaluate(command);
                } else {
                    p.sendMessage(LocaleManager.getString("error.vote.minplayer") +" Minimum: "+ command.getMinimum_players());
                }
            } else {
                command.makeHappen();
            }



        }
        locked=false;

    }

    public float getCountRealPlayers(Player[] players, String command) {
        

        if (BukkitVote.Permissions != null) {
            
                float i = 0;
                for (Player p : players) {
                 if (BukkitVote.Permissions.has(p, "bukkitvote.vote."+command)||BukkitVote.Permissions.has(p, "bukkitvote.accept")) {
                
                 if (bv.getServer().getPluginManager().isPluginEnabled("Citizens")) {
                        if(!com.citizens.npcs.NPCManager.isNPC(p)) {
                            i=i+1;
                        }
                    } else {
                        i = i + 1;
                    }
                    }
                }
                return i;
        

        } else {

            if (bv.getServer().getPluginManager().isPluginEnabled("Citizens")) {
                float i = 0;
                for (Player p : players) {
                    if (!com.citizens.npcs.NPCManager.isNPC(p)) {
                        i = i + 1;
                    }
                }
                return i;
            } else {
                return (float) players.length;
            }
        }

    }

    private void evaluate(VoteCommand command) {
        float i = 0;
        Player[] onlinePlayers;
        if (command.getWorld() != null) {
            onlinePlayers = command.getWorld().getPlayers().toArray(new Player[0]);
        } else {
            onlinePlayers = bv.getServer().getOnlinePlayers();
        }

        float size = getCountRealPlayers(onlinePlayers, command.getPermissionNodeName());
        for (Player p : onlinePlayers) {
            List<VoteCommand> list = votelist.get(p);
            if (list != null) {
                if (list.contains(command)) {
                    //System.out.println("Player: "+p.getName());
                    i++;
                }
            }

        }
        float div = i / size;
        
       // System.out.println("Votes " + i + " user in List " + size + " Faktor" + div);
        if (div >= ((float) command.getPercentage_to_success() / 100.0)) {
           
            int state = command.getState();
            if (last.size() > 0) {
                for (int j = 0; j < last.size(); j++) {
                    VoteCommand vc = last.get(j);
                    // System.out.println(state + "   " + vc.getState());
                    if (state == vc.getState()) {
                        vc.deactivate();
                        last.remove(vc);
                    }
                }

            }
            ChangeState whatHappened = command.makeHappen();
             
            bv.broadcastMessage(LocaleManager.getString("vote.succ", this.succMap(command.getVoteName(), command.getInfo())));
            if (whatHappened.hasPermanentChange()) {
                //System.out.println("Adding:" + command.getVoteName());
                last.add(command);
            }



            clearList(command);
        } else {
            HashMap<String,String> formatMap = new HashMap<String,String>();
            formatMap.put("PLAYERSVOTED", ""+(int)i);
            formatMap.put("ALLPLAYERS", ""+(int)size);
            formatMap.put("VOTENAME", command.getVoteName());
            
            bv.broadcastMessage(LocaleManager.getString("vote.status", formatMap));
            
            bv.broadcastMessage(LocaleManager.getString("vote.status2", formatMap));
        }

    }

    private void clearList(VoteCommand co) {
        Collection<List<VoteCommand>> c = votelist.values();
        for (List<VoteCommand> ls : c) {
            if (ls.contains(co)) {
                ls.remove(co);
            }
        }
    }

    public String getInfo(World world) {
        String s = "Current time: " + (world.getTime()) + " \n";
        for (VoteCommand vc : last) {
            s += vc.getVoteName()+ " "+ vc.getInfo() + " ";
        }
        return s;
    }
    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
//        System.out.println("Listening join of "+event.getPlayer().toString());
//        System.out.println(bv.getServer().getDefaultGameMode().toString());
        if(bv.getServer().getDefaultGameMode().equals(GameMode.CREATIVE)) {
//           System.out.println("[BV] Making creative");
            if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            event.getPlayer().setGameMode(GameMode.CREATIVE);
//            System.out.println("Setting Creative");
            }
        } else if(bv.getServer().getDefaultGameMode().equals(GameMode.SURVIVAL)) {
//            System.out.println("[BV] Making survival");
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }
//        public void onPlayerLogin(PlayerLoginEvent event) {
//        System.out.println("[BV] Making creative on login");
//        System.out.println("[BV]")
//        if(bv.getServer().getDefaultGameMode().equals(GameMode.CREATIVE)) {
//            System.out.println("[BV] Making creative");
//            if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
//            event.getPlayer().setGameMode(GameMode.CREATIVE);
//        } else if(bv.getServer().getDefaultGameMode().equals(GameMode.SURVIVAL)) {
//            
//            event.getPlayer().setGameMode(GameMode.SURVIVAL);
//        }
//    }
    

    
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if(!from.getWorld().equals(to.getWorld())){
            List<VoteCommand> list = votelist.get(event.getPlayer());
            if(list!=null){
                for(VoteCommand vc: list) {
                    if(!vc.isGlobal()) {
                        list.remove(vc);
                    }
                }
            }
        }
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        locked=true;
        votelist.remove(event.getPlayer());
        locked=false;
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) {
        locked=true;
        votelist.remove(event.getPlayer());
        locked=false;
    }
    private HashMap<String,String> voteMap(String player, String votename, String rate, String time) {
        HashMap<String,String> output = new HashMap<String,String>();
        output.put("PLAYER", player);
        output.put("VOTENAME", votename);
        output.put("RATE", rate);
        output.put("TIME", time);
        return output;
        
    }
    private HashMap<String,String> succMap(String votename, String info) {
        HashMap<String,String> output = new HashMap<String,String>();
        output.put("ONOFF", info);
        output.put("VOTENAME", votename);
        return output;
    }
}
