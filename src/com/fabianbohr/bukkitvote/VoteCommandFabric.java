/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;

import com.fabianbohr.bukkitvote.commands.MuteCommand;
import java.util.*;

import com.fabianbohr.bukkitvote.commands.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.command.*;

/**
 *
 * @author ethernity
 */
public class VoteCommandFabric {

    private static VoteCommandFabric instance = new VoteCommandFabric();
    private List<String> commandReference;
    private HashMap<String, HashMap<String, Integer>> valueReference = null;

    private VoteCommandFabric() {
        commandReference = new ArrayList<String>();
    }

    public static VoteCommandFabric getInstance() {
        return instance;
    }

    public String[] getAllVoteCommands() {
        return commandReference.toArray(new String[0]);
    }

    public boolean hasPermissions(Player p, String command) {
        String node = "bukkitvote.vote." + command;
        if (p.hasPermission(node)) {
            return true;
        } else if (BukkitVote.Permissions != null && p != null) {
            if (BukkitVote.Permissions.has(p, node)) {
                return true;
            } else {
                p.sendMessage(LocaleManager.getString("error.permissions"));
                return false;
            }
        } else {
            return true;
        }


    }

    public VoteCommand getVoteCommand(String[] args, BukkitVote vote, Player p) {
        World w;
        if (p == null) {
            w = null;
        } else {
            w = p.getWorld();
        }
        if (valueReference == null) {
            setValueReference(VoteCommandFabric.getDefaultConfig(vote.majority, vote.minplayers, vote.time));
        }
        if (args != null && args.length > 0) {
            if (commandReference.contains(args[0])) {
                if (args[0].equals("help")) {
                    return new HelpCommand(p, vote);
                } else /*if (hasPermissions(p, args[0]))*/ {
                    if (args[0].equals("eternalday")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new EternalCommand(w, ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("eternalnight")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new EternalNightCommand(w, ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("kick")) {
                        //System.out.println(java.util.Arrays.toString(args));
                        if (p == null) {
                            int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                            return new KickCommand(null, ar[0], ar[1], ar[2]);
                        } else if (args.length > 1) {
                            String playername = args[1];
                            Player toKick = vote.getServer().getPlayer(playername);
                            if (toKick != null) {
                                int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                                return new KickCommand(toKick, ar[0], ar[1], ar[2]);
                            } else {

                                p.sendMessage(LocaleManager.getString("error.notonserver", LocaleManager.replacePlayer(playername)));
                                return null;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Usage: /vote kick <name>");
                            return null;
                        }
                    } else if (args[0].equals("ban")) {
                        //System.out.println(java.util.Arrays.toString(args));
                        if (p == null) {
                            int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                            return new BanCommand(null, null, ar[0], ar[1], ar[2]);
                        } else if (args.length > 1) {
                            String playername = args[1];
                            Player toBan = vote.getServer().getPlayer(playername);
                            if (toBan != null) {
                                int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                                return new BanCommand(toBan, vote, ar[0], ar[1], ar[2]);
                            } else {

                                p.sendMessage(LocaleManager.getString("error.notonserver", LocaleManager.replacePlayer(playername)));
                                return null;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Usage: /vote ban <name>");
                            return null;
                        }
                    } else if (args[0].equals("switchmode")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new GameModeCommand(vote.getServer(), ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("day")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new TimeChangeCommand(true, w, ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("night")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new TimeChangeCommand(false, w, ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("storm")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new StormCommand(w, ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("thunder")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        return new ThunderCommand(w, ar[0], ar[1], ar[2]);
                    } else if (args[0].equals("mute")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        if (p == null) {

                            return new MuteCommand(null, null, ar[0], ar[1], ar[2]);
                        } else if (args.length > 1) {
                            String playername = args[1];
                            Player toMute = vote.getServer().getPlayer(playername);
                            if (toMute != null) {
                                return new MuteCommand(toMute, vote, ar[0], ar[1], ar[2]);
                            } else {

                                p.sendMessage(LocaleManager.getString("error.notonserver", LocaleManager.replacePlayer(playername)));
                                return null;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Usage: /vote mute <name>");
                            return null;
                        }
                    } else if (args[0].equals("unmute")) {
                        int[] ar = this.getMinAndSuc(args[0], vote.majority, vote.minplayers, vote.time);
                        if (p == null) {

                            return new UnmuteCommand(null, null, ar[0], ar[1], ar[2]);
                        } else if (args.length > 1) {
                            String playername = args[1];
                            Player toUnmute = vote.getServer().getPlayer(playername);
                            if (toUnmute != null && vote.getMuteListener().isMuted(playername)) {
                                return new UnmuteCommand(toUnmute, vote, ar[0], ar[1], ar[2]);
                            } else {

                                p.sendMessage(LocaleManager.getString("mute.notmuted", LocaleManager.replacePlayer(playername)));
                                return null;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Usage: /vote mute <name>");
                            return null;
                        }
                    }
                }
            } /*else {
            ConsoleCommandSender console = new ConsoleCommandSender(vote.getServer());
            String message = "/";
            for(String argument : args) {
            message += argument + " ";
            }
            console.sendMessage(message);
            }*/
        }
        return null;



    }

    private int[] getMinAndSuc(String name, int default_pts, int default_mp, int default_vt) {

        if (commandReference.contains(name)) {
            HashMap<String, Integer> map = valueReference.get(name);

            if (map != null) {
                int[] ret = new int[3];
                ret[2] = map.containsKey(BukkitVote.VOTE_TIME) ? map.get(BukkitVote.VOTE_TIME) : default_vt;
                ret[1] = map.containsKey(BukkitVote.MINIMUM_PLAYERS) ? map.get(BukkitVote.MINIMUM_PLAYERS) : default_mp;
                ret[0] = map.containsKey(BukkitVote.SUCCESS_RATE) ? map.get(BukkitVote.SUCCESS_RATE) : default_pts;
                return ret;

            }
        }
        //System.err.println("Not in value reference");
        int[] ar = {default_pts, default_mp, default_vt};
        return ar;
    }

    public static HashMap<String, HashMap<String, Integer>> getDefaultConfig(int mp, int pts, int vt) {
        HashMap<String, HashMap<String, Integer>> vlist = new HashMap<String, HashMap<String, Integer>>();

        vlist.put("day", getDefault(mp, pts, vt));
        vlist.put("night", getDefault(mp, pts, vt));
        vlist.put("eternalnight", getDefault(mp, pts, vt));
        vlist.put("eternalday", getDefault(mp, pts, vt));
        vlist.put("mute", getDefault(mp, pts, vt));
        vlist.put("unmute", getDefault(mp, pts, vt));
        vlist.put("kick", getDefault(mp, pts, vt));
        vlist.put("ban", getDefault(mp, pts, vt));
        vlist.put("storm", getDefault(mp, pts, vt));
        vlist.put("thunder", getDefault(mp, pts, vt));
        vlist.put("help", new HashMap<String, Integer>());
        vlist.put("switchmode", getDefault(mp,pts,vt));
        return vlist;
    }

    private static HashMap<String, Integer> getDefault(int mp, int pts, int vt) {
        HashMap<String, Integer> defconf = new HashMap<String, Integer>();
        defconf.put(BukkitVote.SUCCESS_RATE, pts);
        defconf.put(BukkitVote.MINIMUM_PLAYERS, mp);
        defconf.put(BukkitVote.VOTE_TIME, vt);
        return defconf;

    }

    public void setCommandReference(List<String> ls) {
        commandReference = ls;
    }

    public void setValueReference(HashMap<String, HashMap<String, Integer>> list) {
        valueReference = list;
    }
}
