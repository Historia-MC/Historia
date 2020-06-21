package dev.boooiil.historia.jobs;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.ActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.Job;
import com.gamingmesh.jobs.container.JobInfo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

public class DoJobsPayment {

    public static void payout(Player player, String jobName) {

        Economy economy = Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

        Job job = Jobs.getJob(jobName);

        ActionInfo actionInfo = null;

        int count = 0;

        for (JobInfo jobInfo : job.getJobInfo(ActionType.BREAK)) {
            
            System.out.println("[" + count + "] " + jobInfo);

            count += 1; 
        }

        //JobInfo jobInfo = job.getJobInfo(actionInfo, Jobs.getPlayerManager().getJobsPlayer(player).getJobProgression(job).getLevel());
        
    }
   
}