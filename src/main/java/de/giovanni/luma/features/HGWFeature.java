package de.giovanni.luma.features;

import de.byteandbit.velociraptor.api.chat.ChatMessage;
import de.byteandbit.velociraptor.api.chat.ChatPriority;
import de.byteandbit.velociraptor.api.data.item.Item;
import de.byteandbit.velociraptor.api.data.item.ItemPrice;
import de.byteandbit.velociraptor.api.data.item.ItemType;
import de.byteandbit.velociraptor.api.events.EventHandler;
import de.byteandbit.velociraptor.api.events.purchase.AfterPurchaseEvent;
import de.giovanni.luma.Luma;
import de.giovanni.luma.data.HGWData;
import de.giovanni.luma.velo.VeloLoader;

import java.text.NumberFormat;
import java.util.Locale;

public class HGWFeature {

    private final NumberFormat FORMATTER = NumberFormat.getNumberInstance(Locale.GERMANY);

    @EventHandler
    public void afterPurchase(AfterPurchaseEvent event) {
        for (Item item : event.getAcceptedItems()) {
            for (ItemPrice price : item.getPrices()) {
                if (price.getType() != ItemType.BUY) {
                    continue;
                }

                HGWData hgw = getHGW(price.getPrice());
                if (hgw == null) {
                    continue;
                }

                hgw.lastUser(event.getPlayerName());
                hgw.lastAmount(event.getAmountOf(item));
                hgw.count(hgw.count() + event.getAmountOf(item));

                for (String message : hgw.messages()) {
                    String finalMessage = message
                            .replaceAll("%winners%", FORMATTER.format(getWinnerCount()))
                            .replaceAll("%winner%", hgw.lastUser())
                            .replaceAll("%amount%", FORMATTER.format(hgw.lastAmount()))
                            .replaceAll("%count%", FORMATTER.format(hgw.count()))
                            .replaceAll("%value%", FORMATTER.format(hgw.identifier()));

                    if (message.startsWith("/pholo")) {
                        Luma.getHoloQueue().addToQueue(finalMessage);
                        continue;
                    }

                    VeloLoader.API.getChatAPI().send(message.startsWith("/") ? ChatMessage.command().text(finalMessage.substring(1)).priority(ChatPriority.LOW) : ChatMessage.chat().text(finalMessage).priority(ChatPriority.LOW));
                }
            }
        }

        Luma.getSettings().getConfiguration().setValue("hgws", Luma.getSettings().getHgws());
    }

    private HGWData getHGW(double identifier) {
        return Luma.getSettings().getHgws().stream().filter(action -> action.identifier() == identifier).findFirst().orElse(null);
    }

    private int getWinnerCount() {
        return Luma.getSettings().getHgws().stream().mapToInt(HGWData::count).sum();
    }
}