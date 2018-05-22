package com.example.pranav.helloandroid;

import android.app.VoiceInteractor;
import android.graphics.Path;

import java.io.Serializable;
import java.util.ArrayList;

public class OptionNode implements Serializable{
    private int NodeId;
    private String Name;
    private String Description;
    private OptionType OptionType;
    private ArrayList<OptionNode> ChildrenNodes;
    private boolean IsSelected;

    public OptionNode(int nodeId, String name, String description, OptionType optionType){
        NodeId = nodeId;
        Description = description;
        Name = name;
        OptionType = optionType;
        IsSelected = false;
    }

    public void AddChild(OptionNode child){
        if(ChildrenNodes == null || ChildrenNodes.isEmpty()){
            ChildrenNodes = new ArrayList<>();
        }
        ChildrenNodes.add(child);
    }

    public OptionNode Clone(OptionNode original){
        OptionNode result = new OptionNode(original.NodeId, original.Name, original.Description, original.OptionType);

        if(original.get_childrenNodes() == null || original.get_childrenNodes().size() == 0){
            result.set_childrenNodes(null);
            return result;
        }

        ArrayList<OptionNode> selectedChildren = original.getSelectedChildren();

        if(selectedChildren == null || selectedChildren.size() == 0){
            result.set_childrenNodes(null);
            return result;
        }

        ArrayList<OptionNode> selectedChildrenClones = new ArrayList<>();
        for(int i = 0; i < selectedChildren.size(); i++){
            OptionNode child = new OptionNode(selectedChildren.get(i).NodeId, selectedChildren.get(i).Name, selectedChildren.get(i).Description, selectedChildren.get(i).OptionType);
            selectedChildrenClones.add(child);
        }

        //Assuming that multi selection will always come in end
        if(original.OptionType == OptionType.MULTIPLE){
            result.set_childrenNodes(selectedChildrenClones);
            return result;
        }

        OptionNode childClone = Clone(selectedChildren.get(0));
        result.AddChild(childClone);

        return result;
    }

    public void select(){
        IsSelected = true;
    }

    public void unselect(){
        IsSelected = false;
    }

    public int get_nodeId(){
        return NodeId;
    }

    public String get_name() {
        return Name;
    }

    public String get_description() {
        return Description;
    }

    public OptionType get_optionType() {
        return OptionType;
    }

    public ArrayList<OptionNode> get_childrenNodes() {
        return ChildrenNodes;
    }

    public void set_childrenNodes(ArrayList<OptionNode> _children) {
        ChildrenNodes = _children;
    }

    public boolean get_isSelected() {
        return IsSelected;
    }

    public ArrayList<OptionNode> getSelectedChildren(){
        ArrayList<OptionNode> selected = new ArrayList<>();
        for(int i = 0; i < ChildrenNodes.size(); i++){
            OptionNode tNode = ChildrenNodes.get(i);
            if(tNode.get_isSelected()){
                selected.add(tNode);
            }
        }
        return selected;
    }
}
