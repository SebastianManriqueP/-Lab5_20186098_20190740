package com.example.lab5_aiot.Model;

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {
    private List<Result> results;

    public List<Result> getResult() {
        return results;
    }

    public void setResult(List<Result> result) {
        this.results = result;
    }
}
