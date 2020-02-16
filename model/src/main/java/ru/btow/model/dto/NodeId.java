package ru.btow.model.dto;

import java.util.ArrayList;

/**
 * Created by btow on 15.02.2020.
 */
public class NodeId extends ArrayList<Integer>{

    @Override
    public String toString() {
        int[] pointCounter = {this.size(), 1};
        String[] result = {""};
        this.forEach(levelNumber -> {
            result[0] += String.valueOf(levelNumber);
            if (pointCounter[0] > pointCounter[1]){
                result[0] += ".";
            }
            pointCounter[1]++;
        });
        return result[0];
    }

    @Override
    public boolean equals(Object o) {
        final NodeId[] nodeIds = {((NodeId)o)};
        final boolean[] result = {this.size() == nodeIds[0].size()};
        if (result[0]){
            final int[] counter = {0};
            this.forEach(number -> {
                result[0] &= number == nodeIds[0].get(counter[0]++);
            });
        }
        return result[0];
    }
}
