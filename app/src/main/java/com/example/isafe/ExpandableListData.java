package com.example.isafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListData {

    public static HashMap<String, List<String>> getData() {

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> TrendSetter = new ArrayList<String>();

        TrendSetter.add("Trend Setters consists of activities concerned with awareness programs and filling the infrastructural gap");


        List<String> gameChanger = new ArrayList<String>();

        gameChanger.add("abcdefg");


        List<String> peaceMaker = new ArrayList<String>();

        peaceMaker.add("abcdefg");

        List<String> LifeSaver = new ArrayList<String>();

        LifeSaver.add("abcdefg");

        expandableListDetail.put("Peace Makers", peaceMaker);

        expandableListDetail.put("Game Changer", gameChanger);

        expandableListDetail.put("Trend Setters", TrendSetter);

        expandableListDetail.put("Life Saver", LifeSaver);




        return expandableListDetail;

    }


}