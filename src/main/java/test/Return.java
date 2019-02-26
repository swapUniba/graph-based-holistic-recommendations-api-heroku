package test;

import java.util.HashMap;

public class Return {
    private HashMap recommendation;

    public Return(){
    }

    public Return(HashMap recommendation){
        this.recommendation=recommendation;
    }

    public HashMap getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(HashMap recommendation) {
        this.recommendation = recommendation;
    }


}
