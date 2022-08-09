package dev.boooiil.historia.classes;

import java.util.Date;

public class KillerUser {
    
    private int kills = 0;
    private Long lastKill = 0l;
    private boolean hitBack = false;

    public int getKills() {

        return kills;

    }

    public Long getLastKill() {

        return lastKill;

    }

    public boolean getLastHit() {

        return hitBack;

    }

    public void addKill() {

        Long time = new Date().getTime();

        this.kills++;
        this.lastKill = time;

    }

}
