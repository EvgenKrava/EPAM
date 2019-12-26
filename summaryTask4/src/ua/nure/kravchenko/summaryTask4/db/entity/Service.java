package ua.nure.kravchenko.summaryTask4.db.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service extends Entity {
    private String name;
    private List<Tariff> tariffs;

    public Service(){
        tariffs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    @Override
    public String toString() {
        return name + " " + tariffs;
    }
}
