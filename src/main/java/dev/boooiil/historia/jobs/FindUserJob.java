package dev.boooiil.historia.jobs;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.Job;

import org.bukkit.entity.Player;

public class FindUserJob {

    public boolean hasJob(Player player, String jobSearch) {
        //Jobs stuff
        if (Jobs.getJob(jobSearch) instanceof Job) {

            if (Jobs.getPlayerManager().getJobsPlayer(player).isInJob(Jobs.getJob(jobSearch))) return true;
            else return false;

        } return false;
    }
}