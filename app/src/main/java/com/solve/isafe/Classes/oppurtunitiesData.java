package com.solve.isafe.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class oppurtunitiesData {

    public static HashMap<String, List<String>> getData() {

        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> ui = new ArrayList<String>();

        ui.add("absddjskdjs");

        List<String> web = new ArrayList<String>();

        web.add("abcdefg");

        List<String> app = new ArrayList<String>();

        app.add("abcdefg");


        expandableListDetail.put("Web Developer", web);

        expandableListDetail.put("App Developer", app);

        expandableListDetail.put("UI/UX Designer", ui);

        return expandableListDetail;

    }


}