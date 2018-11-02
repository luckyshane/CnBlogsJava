package com.luckyshane.cnblogs.model.db;

import com.luckyshane.cnblogs.App;
import com.luckyshane.cnblogs.model.db.entity.ReadStateBean;
import com.luckyshane.cnblogs.model.db.entity.ReadStateBean_;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.objectbox.Box;

public class DBHelper implements IDBHelper {

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void insertReadBlogId(String blogId) {
        try {
            getReadStateBox().put(new ReadStateBean(0, blogId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isBlogRead(String blogId) {
        List<ReadStateBean> readStateBeanList = getReadStateBox().getAll();
        if (readStateBeanList != null) {
            for (ReadStateBean bean : readStateBeanList) {
                if (bean.blogId.equals(blogId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<String> getAllReadIds() {
        String[] readIds = getReadStateBox().query().build().property(ReadStateBean_.blogId).findStrings();
        if (readIds == null || readIds.length == 0) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(readIds);
    }

    private Box<ReadStateBean> getReadStateBox() {
        return App.getInstance().getBoxStore().boxFor(ReadStateBean.class);
    }

    private static class SingletonHolder {
        private static DBHelper INSTANCE = new DBHelper();
    }


}
