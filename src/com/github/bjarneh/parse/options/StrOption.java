package com.github.bjarneh.parse.options;

import java.util.ArrayList;

class StrOption extends Option{

    ArrayList<String> arguments;

    StrOption(String[] flags){
        super(flags);
        arguments = new ArrayList<String>(10);
    }

    void add(String arg){ this.arguments.add(arg); }
    void reset(){ this.arguments.clear(); }
    boolean isSet(){ return this.arguments.size() > 0; }
    String get(){ return this.arguments.get(0); }
    String[] getAll(){
        String[] args = new String[this.arguments.size()];
        this.arguments.toArray(args);
        return args;
    }

    public String toString(){
        return "%37s : %s".format(flagStr(), arguments);
    }
}
