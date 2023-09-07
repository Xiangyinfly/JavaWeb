package com.fruit.service.impl;

import com.fruit.pojo.Fruit;
import com.fruit.service.fruitService;
import com.fruit.dao.fruitDao;
import com.fruit.dao.impl.fruitDaoImpl;
import java.util.List;

public class fruitServiceImpl implements fruitService {
    private fruitDaoImpl fruitDao = null;
    @Override
    public List<Fruit> getFruitList(String keyword, Integer page) {
        return fruitDao.getFruitList(page,keyword);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDao.addFruit(fruit);
    }

    @Override
    public Fruit getFruitById(int fid) {
        return fruitDao.getFruitById(fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDao.updateFruit(fruit);
    }

    @Override
    public void deleteFruit(int fid) {
        fruitDao.deleteFruit(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        return (fruitDao.getTotalCount(keyword) - 1) / 5 + 1;
    }
}
