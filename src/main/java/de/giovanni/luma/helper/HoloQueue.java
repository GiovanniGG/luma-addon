package de.giovanni.luma.helper;

import de.byteandbit.velociraptor.api.chat.ChatMessage;
import de.giovanni.luma.velo.VeloLoader;

import java.util.concurrent.LinkedBlockingQueue;

public class HoloQueue {

    private LinkedBlockingQueue<String> queue;

    public HoloQueue() {
        queue = new LinkedBlockingQueue<>();

        Scheduler.scheduleRepeatingTask(this::poll, 10 * 1000, 0);
    }

    private void poll() {
        String cmd = queue.poll();

        if (cmd != null) {
            VeloLoader.API.getChatAPI().send(ChatMessage.command().text(cmd.substring(1)));
        }
    }

    public void addToQueue(String cmd) {
        queue.add(cmd);
    }
}