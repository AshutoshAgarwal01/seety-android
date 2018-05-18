package com.example.pranav.helloandroid;

public class GlobalVariable{
    private static OptionNode _topCategoryNode;
    private static OptionNode _activityOptionNode;

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
}
