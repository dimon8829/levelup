package com.dihri.sport.test.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "levelup")
public class LevelupProperties {
    private Map<Integer,String> levelupMap;

    public Map<Integer, String> getLevelupMap() {
        return levelupMap;
    }

    public void setLevelupMap(Map<Integer, String> levelupMap) {
        this.levelupMap = levelupMap;
    }
}
