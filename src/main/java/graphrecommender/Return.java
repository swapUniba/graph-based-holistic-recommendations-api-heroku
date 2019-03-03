package graphrecommender;

import java.util.HashMap;

public class Return {
    private HashMap recommendation;

    //anche qua il mapping viene fatto in automatico dal server
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
