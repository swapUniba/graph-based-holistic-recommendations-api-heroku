package test;


import java.awt.Dimension;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.scoring.PageRank;
import edu.uci.ics.jung.algorithms.scoring.PageRankWithPriors;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Grafo {

    private Graph graph;
    private Request request;
    private HashMap<String, String[]> map;
    private int archi_PC = 0, archi_CD = 0, archi_DL = 0;
    private boolean debug=false;

    public Grafo(Request req) throws IOException {
        //setto livello di debug, si può togliere volendo, salvo richiesta e imposto la città
        //debug=true;
        this.request=req;
        FromFile.SetData(request.getCity());

        //Inizializzo il grafo
        graph = new OrderedSparseMultigraph<>();

        //Create user nodes
        String utente="P_"+ request.getUser();
        graph.addVertex(utente); //Livello 0
        if(debug) System.out.println("Ho collegato aggiunto "+utente);

        if(debug) System.out.println("Livello 0-1");
        //NB: è stato realizzato solo il caso con un utente, il caso multi utente è riservato a sviluppi futuri
        if (request.isFull_connected()) {
            request.getHistory().keySet().forEach((i) -> graph.addEdge("PC:" + (++archi_PC), new Pair<>(utente, "C_" + i), EdgeType.DIRECTED));
            if(debug) request.getHistory().keySet().forEach((i) -> System.out.println("Ho collegato "+utente+" con "+ "C_"+i));
        }
        else{
            for (String contesto: request.getContesto()) {
                graph.addEdge("PC:" + (++archi_PC), new Pair<>(utente, "C_"+contesto), EdgeType.DIRECTED);
                if(debug) System.out.println("Ho collegato "+utente+" con " + contesto);
            }
        }//Livello 0-1

        if(debug) System.out.println("Livello 1-2");
        request.getHistory().keySet().forEach((i) -> {
            for (String descrizione: request.getHistory().get(i)) {
                graph.addEdge("CD:" + (++archi_CD), new Pair<>("C_"+i, "D_"+descrizione), EdgeType.DIRECTED);
                if(debug) System.out.println("Ho collegato "+i+" con "+ descrizione);
            }
        });//Livello 1-2

        if(debug) System.out.println("Livello 2-3");
        //Leggo da file
        map = FromFile.getPlacesNew();
        map.keySet().forEach((i) -> {
            for (int j = 0; j < map.get(i).length; j++) {
                if(request.isDiretto()) graph.addEdge("DL:" + (++archi_DL), new Pair<>("D_"+map.get(i)[j], "L_"+i), EdgeType.DIRECTED);
                else graph.addEdge("DL:" + (++archi_DL), new Pair<>("D_"+map.get(i)[j], "L_"+i), EdgeType.UNDIRECTED);
                if(debug) System.out.println("Ho collegato "+map.get(i)[j]+" con "+ i);
            }
        });//Livello 2-3
    }

    public HashMap<String, Double> Pagerank() {

        HashMap<String, Double> score_map  = new HashMap();
        PageRankWithPriors ranker;

        //Scelgo l'algoritmo richiesto

        if(debug) System.out.println("dentro");
        if(request.getAlg().equals("PageRankPriors")) ranker = new PageRankWithPriors(graph, PriorsFunction.getPriorsFunction(request), request.getAlpha());
        else ranker = new PageRank(graph, request.getAlpha());

        //calcola PageRank
        ranker.evaluate();

        //salvo risultato solo per i Luoghi
        for (Object v : graph.getVertices()) {
            if (v.toString().contains("L_") && !ranker.getVertexScore(v).toString().equals("0.0")) {
                score_map.put(v.toString(), (Double) ranker.getVertexScore(v));
            }
        }

        if(debug) System.out.println(map);

        //ordinamento mappa in base allo score
        Object[] ordinati = score_map.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue())).toArray();


        //svuoto la vecchia mappa
        score_map = new HashMap();

        if(debug) System.out.println(score_map);

        //prendo i primi top_rank. NB: qua li mette bene però nel JSON li mette comunque al contrario.
        for (int i =0 ; i< request.getTop_rank(); i++) {
            String nome = ordinati[i].toString();
            //String score = nome.substring(nome.indexOf("=") + 1);
            nome = nome.substring(nome.indexOf("_") + 1, nome.indexOf("="));
            score_map.put(nome, i+1.0);
        }

        return score_map;
    }

}
