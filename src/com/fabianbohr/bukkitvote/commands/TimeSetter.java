/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fabianbohr.bukkitvote.commands;

import org.bukkit.*;

/**
 *
 * @author ethernity
 */
public class TimeSetter implements Runnable {

    long delay;
    static int eternalday = 1;
    static int eternalnight = 2;
    World toWatch;
    int whatToDo;
    boolean activated;

    public TimeSetter(World w, long delay, int what) {
        toWatch = w;
        this.delay = delay;
        whatToDo = what;
        activated = true;
    }

    public void run() {
        try {
            
            while (activated) {

                long time = toWatch.getTime();
               // System.out.println("Current Time: "+time);
                if (whatToDo == eternalday) {
                    if (time >11*1000) {
                        toWatch.setTime(0 * 1000);
                    }
                }
                else if (whatToDo == eternalnight) {
                    if (time <14*1000 || time > 18*100) {
                        toWatch.setTime(15 * 1000);
                    }
                }
                Thread.sleep(delay);
            }
            
        } catch (Exception e) {
        }



    }
    public void deactivate() {
        activated=false;
    }


}

