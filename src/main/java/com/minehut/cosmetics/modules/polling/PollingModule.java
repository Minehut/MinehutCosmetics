package com.minehut.cosmetics.modules.polling;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class PollingModule<T> {

    private @NotNull T state;
    private long pollRate;
    private BukkitTask task = null;

    private Plugin plugin;


    public PollingModule(Plugin plugin, @NotNull T base, long pollRate) {
        this.plugin = plugin;
        this.state = base;
        this.pollRate = pollRate;
    }

    public abstract Optional<T> poll();

    protected void process(T state) {
        // process nothing by default
    }

    /**
     * Initializes the polling module, tries to pull data once
     * syncronously then processes future requests for data async
     */
    public void init() {
        cancel();
        forcePollAndProcess();
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::forcePollAndProcess, 0, pollRate);
    }

    /**
     * Polls and processes state syncronously
     */
    public void forcePollAndProcess() {
        poll().ifPresent(state -> {
            process(state);
            this.state = state;
        });
    }

    public void cancel() {
        if (task == null) return;
        task.cancel();
        task = null;
    }


    public @NotNull T state() {
        return state;
    }

}
