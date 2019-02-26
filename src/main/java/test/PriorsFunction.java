package test;

import com.google.common.base.Function;

import java.util.*;

public class PriorsFunction {

    public static Function getPriorsFunction(Request request){
        ArrayList<String> contesto = new ArrayList<>();
        contesto.add("P_"+request.getUser());
        for (int i = 0; i < request.getContesto().length ; i++) {
            contesto.add("C_"+request.getContesto()[i]);
        }
        //System.out.println(contesto);
        switch (request.getPriors()){
            case "standard": return getPriorsFunctionDefault1(contesto);
            case "standardContesto": return getPriorsFunctionDefault2(contesto);
            case "standardCategorie": return getPriorsFunctionDefault3(contesto,request.getHistory().keySet());
            case "standardContestoCategorie": return getPriorsFunctionDefault4(contesto, request.getHistory().keySet());
            default: return null;
        }
    }

    public static Function getPriorsFunctionDefault1(List contesto){
    Function f1 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else return 0.0;
    });

    return f1;
}
    public static Function getPriorsFunctionDefault2(List contesto){
    Function f2 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else if (i.toString().startsWith("C_")) return 0.3;
        else return 0.0;
    });
        return f2;
}
    public static Function getPriorsFunctionDefault3(List contesto, Set pref_c){
    Function f3 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else if (pref_c.contains(i.toString().substring(2))) return 0.5;
        else return 0.0;
    });
        return f3;
}
    public static Function getPriorsFunctionDefault4(List contesto, Set pref_c){
    Function f4 = ((Object i) -> {
        if (contesto.contains(i)) return 1.0;
        else if (i.toString().startsWith("C_")) return 0.3;
        else if (pref_c.contains(i.toString().substring(2))) return 0.5;
        else return 0.0;
    });
        return f4;
    }
}
