package com.github.bjarneh.parse.options;

class BoolOption extends Option{

    boolean set;

    BoolOption(String[] flags){
        super(flags);
        this.set = false;
    }

    void found(){ this.set = true; }
    void reset(){ this.set = false; }
    boolean isSet(){ return this.set; }

    public String toString() {
        return "%37s : %b".format(flagStr(), set);
    }

}
