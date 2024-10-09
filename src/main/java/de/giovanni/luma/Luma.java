package de.giovanni.luma;

import de.byteandbit.velociraptor.api.VelociraptorAPI;
import de.giovanni.luma.features.HGWFeature;
import de.giovanni.luma.helper.HoloQueue;
import de.giovanni.luma.settings.Settings;
import de.giovanni.luma.velo.VeloLoader;
import lombok.Getter;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class Luma extends LabyModAddon {

    @Getter
    private static Luma instance;

    @Getter
    private static Settings settings;

    @Getter
    private static HoloQueue holoQueue;

    @Override
    public void onEnable() {
        instance = this;

        settings = new Settings();

        holoQueue = new HoloQueue();

        VeloLoader.load(() -> {
            VelociraptorAPI.EVENT_BUS.register(new HGWFeature());
        });
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }
}
