package com.example.diazquiroz.gestorhorario2.api.resultados;

import com.example.diazquiroz.gestorhorario2.api.model.Tienda;

import java.util.ArrayList;

/**
 * Created by DIAZ QUIROZ on 5/06/2018.
 */

public class TrackEntityHolder{
    private String status = "ok";
    private String next;
    private String previous;
    private int count;
    private ArrayList<Tienda> results;

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Tienda> getResults() {
        return results;
    }

    public void setResults(ArrayList<Tienda> results) {
        this.results = results;
    }
}

