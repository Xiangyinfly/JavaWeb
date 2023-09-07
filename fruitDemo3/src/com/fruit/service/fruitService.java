package com.fruit.service;

import com.fruit.pojo.Fruit;

import java.util.List;

public interface fruitService {
    List<Fruit> getFruitList(String keyword,Integer page);
    void addFruit(Fruit fruit);
    Fruit getFruitById(int fid);
    void updateFruit(Fruit fruit);
    void deleteFruit(int fid);
    Integer getPageCount(String keyword);
}
