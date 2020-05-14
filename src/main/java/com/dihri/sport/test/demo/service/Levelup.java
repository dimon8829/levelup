package com.dihri.sport.test.demo.service;

import com.dihri.sport.test.demo.LevelupProperties;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class Levelup {

    private final Map<Integer,Integer> levelupMap;
    private final Integer levelMax;
    private final String symbolLastLevel = "";

    public Levelup(LevelupProperties levelupProperties) {
        checkLevelupProperties(levelupProperties);
        this.levelupMap = new HashMap<>();
        this.levelMax = levelupProperties.getLevelupMap().keySet().stream().max(Comparator.naturalOrder()).get();
        Integer currentExp = 0;
        for (int i=1;i<=levelMax;i++) {
            if(levelupProperties.getLevelupMap().containsKey(i)) currentExp = parseInt(levelupProperties.getLevelupMap().get(i));
            this.levelupMap.put(i,currentExp);
        }
        System.out.println();
    }

    private void checkLevelupProperties(LevelupProperties levelupProperties) {
        if(!levelupProperties.getLevelupMap().containsKey(1)) throw new IllegalStateException("The levelup properties must be first exp!");
    }

    private Integer parseInt(String numberStr) {
        if(numberStr.equals(symbolLastLevel)) return null;
        Integer number = Integer.parseInt(numberStr);
        if(number<=0) throw new IllegalStateException("The exp in levelup must be positive!");
        return number;
    }

    public boolean hasNext(Integer level) {
        return level<levelMax;
    }

    public Integer getMaxExp(Integer level) {
        return levelupMap.get(level);
    }
}
