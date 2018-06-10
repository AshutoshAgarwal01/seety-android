package com.example.pranav.helloandroid;

import java.util.ArrayList;

public class GlobalVariable{
    private static OptionNode _topCategoryNode;
    private static OptionNode _activityOptionNode;
    private static ArrayList<OptionNode> _allCategories;

    public static String test;

    public static OptionNode get_topCategoryNode() {
        return _topCategoryNode;
    }

    public static void set_topCategoryNode(OptionNode node){
        _topCategoryNode = node;
    }

    public static OptionNode get_activityOptionNode() {
        return _activityOptionNode;
    }

    public static void set_activityOptionNode(OptionNode _activityOptionNode) {
        GlobalVariable._activityOptionNode = _activityOptionNode;
    }

    public static ArrayList<OptionNode> get_allCategories() {
        return _allCategories;
    }

    public static void set_allCategories(ArrayList<OptionNode> _allCategories) {
        GlobalVariable._allCategories = _allCategories;
    }
}
