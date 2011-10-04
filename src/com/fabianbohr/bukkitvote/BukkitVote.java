/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote;

import org.bukkit.*;
import org.bukkit.plugin.java.*;
import java.util.*;
import org.bukkit.util.config.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author ethernity
 */
public class BukkitVote extends JavaPlugin {
    public static String version = "0.7";
    public int majority = 66;
    public int minplayers = 0;
    public int time = 0;
    private List<String> commands;
    public final static String SUCCESS_RATE = "percentage-to-success";
    public final static String MINIMUM_PLAYERS = "minimum-players";
    public final static String VOTE_TIME = "vote-time";
    private VoteListener listener;
    private MuteListener muteListener;
    private BanListener banListener;
    public String loc = "en_us";
    public static PermissionHandler Permissions;

    public void onDisable() {
    }

    public void onEnable() {




        if (!getDataFolder().exists()) {
            buildDefaultConfiguration();
        }


        listener = new VoteListener(this);
        muteListener = new MuteListener(this);
        banListener = new BanListener(this);
        Configuration c = getConfiguration();
        majority = c.getInt("default-percentage-to-success", majority);
        minplayers = c.getInt("default-minimum-players", minplayers);
        time = c.getInt("default-vote-time", time);
        loc = c.getString("locale", loc);

        LocaleManager.init(loc);


        HashMap<String, HashMap<String, Integer>> map = (HashMap<String, HashMap<String, Integer>>) c.getProperty("available-commands");

        String[] tcom = map.keySet().toArray(new String[0]);
        commands = new ArrayList<String>();
        commands.addAll(Arrays.asList(tcom));

        VoteCommandFabric.getInstance().setCommandReference(commands);
        VoteCommandFabric.getInstance().setValueReference(map);



        this.getServer().getPluginManager().registerEvent(Type.PLAYER_KICK, listener, Priority.Low, this);
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_QUIT, listener, Priority.Low, this);
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_CHAT, muteListener, Priority.Highest, this);
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_LOGIN, banListener, Priority.Highest, this);
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN, listener, Priority.Normal, this);
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_TELEPORT, listener, Priority.Low, this);
        this.getServer().getPluginManager().registerEvent(Type.PLAYER_GAME_MODE_CHANGE, listener, Priority.Low, this);
        this.getCommand("bvunmute").setExecutor(new UnMuteCommandExecutor(muteListener));
        this.getCommand("vote").setExecutor(new VoteCommandExecutor(listener, this));
        this.getCommand("fvote").setExecutor(new ForceVoteCommand(listener, this));
        this.getCommand("accept").setExecutor(new AcceptCommandExecutor(listener, this));
        setupPermissions();



        System.out.println("[BukkitVote]: Loaded BukkitVote " + version);


    }

    public void buildDefaultConfiguration() {
        Configuration c = getConfiguration();
        if (c != null) {
            c.setProperty("locale", loc);
            c.setProperty("default-vote-time", time);
            c.setProperty("default-percentage-to-success", majority);
            c.setProperty("default-minimum-players", minplayers);

            c.setProperty("available-commands", VoteCommandFabric.getDefaultConfig(minplayers, majority, time));
            if (!c.save()) {
                System.err.println("Unable to persist configuration files, changes will not be saved.");
            }
        }


    }

    public VoteListener getListener() {
        return listener;
    }

    public MuteListener getMuteListener() {
        return muteListener;
    }

    public BanListener getBanListener() {
        return banListener;
    }

    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions) test).getHandler();
            } else {
                //System.out.println("[BukkitVote]: Permission plugin not detected, everybody has a vote");
            }
        }
    }

    public void broadcastMessage(String msg) {
        Server server = getServer();
        Player[] players = server.getOnlinePlayers();
        for (Player p : players) {
            if (p.isOnline()) {
                p.sendMessage(ChatColor.YELLOW + msg);
            }
        }
    }

    public void worldBroadcast(String msg, World w) {
        List<Player> players = w.getPlayers();
        for (Player p : players) {
            if (p.isOnline()) {
                p.sendMessage(ChatColor.YELLOW + msg);
            }
        }

    }
}
