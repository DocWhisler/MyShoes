package com.tech.oma.myshoes.dataobjects;

public interface ListDao {
    List getList(int id);

    List createList(String name);

    void saveList(List list);

    void updateList(List list);

    void deleteList(List list);

    int getMaxId();
}
