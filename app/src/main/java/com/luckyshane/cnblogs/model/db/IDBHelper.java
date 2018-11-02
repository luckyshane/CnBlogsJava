package com.luckyshane.cnblogs.model.db;

import java.util.List;

public interface IDBHelper {

    void insertReadBlogId(String blogId);

    boolean isBlogRead(String blogId);

    List getAllReadIds();


}
