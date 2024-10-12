package de.giovanni.luma.cmds;

import de.giovanni.luma.Luma;
import de.giovanni.luma.data.HGWData;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;

import java.text.NumberFormat;
import java.util.Locale;

public class CmdListener implements MessageSendEvent {

    private final NumberFormat FORMATTER = NumberFormat.getNumberInstance(Locale.GERMANY);

    @Override
    public boolean onSend(String message) {
        if (message.equalsIgnoreCase("/holoupdate")) {
            LabyMod.getInstance().displayMessageInChat("§c§lLUMA §f» §7Die Hologramme von den HGWs werden von der Config geupdated...");

            for (HGWData hgw : Luma.getSettings().getHgws()) {
                for (String hgwMessage : hgw.messages()) {
                    if (!hgwMessage.startsWith("/pholo")) {
                        continue;
                    }

                    String toUpdate = hgwMessage
                            .replaceAll("%winners%", FORMATTER.format(getWinnerCount()))
                            .replaceAll("%winner%", hgw.lastUser() == null ? "Du?" : hgw.lastUser())
                            .replaceAll("%amount%", FORMATTER.format(hgw.lastAmount()))
                            .replaceAll("%count%", FORMATTER.format(hgw.count()))
                            .replaceAll("%value%", FORMATTER.format(hgw.identifier()));

                    Luma.getHoloQueue().addToQueue(toUpdate);
                }
            }

            return true;
        }
        return false;
    }

    private int getWinnerCount() {
        return Luma.getSettings().getHgws().stream().mapToInt(HGWData::count).sum();
    }
}