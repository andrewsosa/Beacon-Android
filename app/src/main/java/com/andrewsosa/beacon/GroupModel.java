package com.andrewsosa.beacon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupModel {

    String key;
    String name;
    int population;

    public GroupModel() {}

    public GroupModel(String key, String groupName) {
        this.key = key;
        this.name = groupName;
        this.population = 1;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getKey() {
        return key;
    }
}
