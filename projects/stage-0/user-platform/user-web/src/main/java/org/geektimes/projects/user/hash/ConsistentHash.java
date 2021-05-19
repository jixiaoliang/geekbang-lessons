package org.geektimes.projects.user.hash;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author jixiaoliang
 * @since 2021/05/19
 **/
public class ConsistentHash {



    public static void main(String[] args) {

        ConsistentHashWithNavigableMap();
    }


    public static void ConsistentHashWithNavigableMap(){
        // creating tree map
        NavigableMap<String, String> treemap = new TreeMap<>();

        // populating tree map
        treemap.put("2", "two");
        treemap.put("1", "one");
        treemap.put("3", "three");
        treemap.put("6", "six");
        treemap.put("5", "five");

        System.out.println("Ceiling entry for 4: "+ treemap.ceilingEntry("4"));
        System.out.println("Ceiling entry for 5: "+ treemap.ceilingEntry("5"));
        System.out.println("Ceiling entry for 1: "+ treemap.ceilingEntry("1"));
        System.out.println("Ceiling entry for 2: "+ treemap.ceilingEntry("2"));
        System.out.println("Ceiling entry for 3: "+ treemap.ceilingEntry("3"));
    }



}
