package com.fruit.dao;

import com.fruit.domain.Fruit;

import java.util.List;

public interface fruitDao {
    List<Fruit> getFruitList();
    Fruit getFruitById(int fid);
    void updateFruit(Fruit fruit);
    void deleteFruit(int fid);
    void addFruit(Fruit fruit);
    int getTotalCount();
}
