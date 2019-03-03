package graphrecommender;

import com.google.common.base.Function;

import java.util.Set;
import java.util.ArrayList;

public class PriorsFunction {

    public static Function getPriorsFunction(Request request){
        ArrayList<String> contesto = new ArrayList<>();
        contesto.add("P_"+request.getUser());

        for (int i = 0; i < request.getContesto().length ; i++) {
            contesto.add("C_"+request.getContesto()[i]);
        }

        double pesi[] = request.getPriors_weights();
        Set pref_c = request.getHistory().keySet();

        Function f = ((Object i) -> {
            if (contesto.contains(i)) return pesi[0];
            else if (i.toString().startsWith("C_")) return pesi[2];
            else if (pref_c.contains(i.toString().substring(2))) return pesi[3];
            else return pesi[1];
        });
        return f;
    }

}
