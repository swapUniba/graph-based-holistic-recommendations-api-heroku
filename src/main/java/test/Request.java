package test;

import com.google.common.base.Function;

import java.util.Map;

public class Request {
    private String[] contesto;
    private Map<String, String[]> history;
    private String user;
    private int top_rank=3;
    private String city="Bari";
    private boolean full_connected=false;
    private boolean diretto=false;
    private Double alpha=0.3;
    private String alg="PageRankPriors";
    private Function f;
    private String priors="standard";


    public Function getF() {
        return f;
    }

    public String getPriors() {
        return priors;
    }

    public void setPriors(String priors) {
        this.priors = priors;
    }

    public void setF(Function f) {
        this.f = f;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Request(){

    }

    public String[] getContesto() {
        return contesto;
    }

    public void setContesto(String[] contesto) {
        this.contesto = contesto;
    }

    public boolean isDiretto() {
        return diretto;
    }

    public void setDiretto(boolean diretto) {
        this.diretto = diretto;
    }

    public Map<String, String[]> getHistory() {
        return history;
    }

    public void setHistory(Map<String, String[]> history) {
        this.history = history;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTop_rank() {
        return top_rank;
    }

    public void setTop_rank(int top_rank) {
        this.top_rank = top_rank;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void print(){
        System.out.println(user + history.toString() + contesto.toString());
    }

    public boolean isFull_connected() {
        return full_connected;
    }

    public void setFull_connected(boolean full_connected) {
        this.full_connected = full_connected;
    }

}
