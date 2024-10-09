package de.giovanni.luma.data;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class HGWData {
    private double identifier;

    private String lastUser;
    private int lastAmount;
    private int count;

    private List<String> messages;
}