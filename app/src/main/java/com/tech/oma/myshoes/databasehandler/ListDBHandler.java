package com.tech.oma.myshoes.databasehandler;

import com.tech.oma.myshoes.dataobjects.List;

public interface ListDBHandler {

    void addList(List list);

    List getListById(int id);

    List getListByName(String name);

    void updateList(List list);

    void deleteShoe(List list);

    int getMaxId();
}
