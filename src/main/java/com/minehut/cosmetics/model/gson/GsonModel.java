package com.minehut.cosmetics.model.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.Locale;

public class GsonModel {

    public static Gson RANK;

    static {
        //TODO: see if we can edit unirest type adapter
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TextColor.class, (JsonDeserializer<TextColor>) (element, type, context) -> {
            try {
                return NamedTextColor.NAMES.value(element.getAsJsonPrimitive().getAsString().toLowerCase(Locale.ROOT));
            } catch (IllegalArgumentException ex) {
                return NamedTextColor.WHITE;
            }
        });
        RANK = builder.create();
    }

}
