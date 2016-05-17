package com.example.edwin.hypeweatherservice.data.yahoo;

import com.example.edwin.hypeweatherservice.data.JSONPopulator;

import org.json.JSONObject;

/**
 * Created by Edwin on 7-5-2016.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
