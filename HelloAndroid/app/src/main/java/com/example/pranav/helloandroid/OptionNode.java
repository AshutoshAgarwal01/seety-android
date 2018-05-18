package com.example.pranav.helloandroid;

import android.graphics.Path;

import java.io.Serializable;
import java.util.ArrayList;

public class OptionNode implements Serializable{
    private int _nodeId;
    private String _name;
    private String _description;
    private OptionType _optionType;
    private ArrayList<OptionNode> _childrenNodes;
    private boolean _isSelected;

    public OptionNode(int nodeId, String name, String description, OptionType optionType){
        _nodeId = nodeId;
        _description = description;
        _name = name;
        _optionType = optionType;
        _isSelected = false;
    }

    public OptionNode Clone(){
        return new OptionNode(_nodeId, _name, _description, _optionType);
    }

    public void AddChild(OptionNode child){
        if(_childrenNodes == null || _childrenNodes.isEmpty()){
            _childrenNodes = new ArrayList<>();
        }
        _childrenNodes.add(child);
    }

    public void select(){
        _isSelected = true;
    }

    public void unselect(){
        _isSelected = false;
    }

    public int get_nodeId(){
        return _nodeId;
    }

    public String get_name() {
        return _name;
    }

    public String get_description() {
        return _description;
    }

    public OptionType get_optionType() {
        return _optionType;
    }

    public ArrayList<OptionNode> get_childrenNodes() {
        return _childrenNodes;
    }

    public boolean get_isSelected() {
        return _isSelected;
    }

    public ArrayList<OptionNode> getSelectedChildren(){
        ArrayList<OptionNode> selected = new ArrayList<>();
        for(int i = 0; i < _childrenNodes.size(); i++){
            OptionNode tNode = (OptionNode) _childrenNodes.toArray()[i];
            if(tNode.get_isSelected()){
                selected.add(tNode);
            }
        }
        return selected;
    }
}
