package com.fruit.dao.impl;

import com.fruit.dao.fruitDao;
import com.fruit.domain.Fruit;
import com.mySSM.baseDao.BaseDao;
import com.mySSM.baseDao.BasicDao;

import java.util.List;

public class fruitDaoImpl implements fruitDao, BasicDao<Fruit> {
    @Override
    public List<Fruit> getFruitList() {
        return multiQuery("select * from fruit",Fruit.class);
    }
    public List<Fruit> getFruitList(int page,String keyword) {
        return multiQuery("select * from fruit where fname like ? or fremark like ? limit ?,5",Fruit.class,"%" + keyword + "%","%" + keyword + "%",(page - 1) * 5);
    }

    public List<Fruit> getFruitList(int page) {
        return multiQuery("select * from fruit limit ?,5",Fruit.class,(page - 1) * 5);
    }

    @Override
    public Fruit getFruitById(int fid) {
        return singleQuery("select * from fruit where fid=?",Fruit.class,fid);
    }

    @Override
    public void updateFruit(Fruit fruit) {
        update("update fruit set fname=?,fprice=?,fcount=?,fremark=? where fid=?",fruit.getFname(),fruit.getFprice(),fruit.getFcount(),fruit.getFremark(),fruit.getFid());
    }

    @Override
    public void deleteFruit(int fid) {
        update("delete from fruit where fid=?",fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        update("insert into fruit values(0,?,?,?,?)",fruit.getFname(),fruit.getFprice(),fruit.getFcount(),fruit.getFremark());
    }

    @Override
    public int getTotalCount() {
        return multiQuery("select * from fruit",Fruit.class).size();
    }

    public int getTotalCount(String keyword) {
        return multiQuery("select * from fruit where fname like ? or fremark like ?",Fruit.class,"%" + keyword + "%","%" + keyword + "%").size();
    }
}
