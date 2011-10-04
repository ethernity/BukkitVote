/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fabianbohr.bukkitvote.commands;

/**
 *
 * @author ethernity
 */
public class ChangeState {

    boolean permanentChange;
    String message;
    static int TIME_STATE=1;
    static int GENERAL_STATE=2;
    static int WEATHER_STATE=3;
    int state;
    public ChangeState(boolean pc, String msg, int whatstate) {
        permanentChange=pc;
        message=msg;
        state=whatstate;
    }
    public boolean hasPermanentChange() {
        return permanentChange;
    }

    public String getMessage() {
        return message;
    }
    public int getState() {
        return state;
    }
    







}
