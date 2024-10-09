package de.giovanni.luma.settings;

import com.google.gson.reflect.TypeToken;
import de.giovanni.luma.data.Configuration;
import de.giovanni.luma.data.HGWData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Settings {

    private Configuration configuration;

    private List<HGWData> hgws;

    public Settings() {
        configuration = new Configuration();

        hgws = configuration.getList("hgws", new TypeToken<List<HGWData>>(){}.getType(), new ArrayList<>());
    }
}