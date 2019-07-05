package com.sephyioth.ldcore.core;

import android.content.Context;

import com.sephyioth.ldcore.model.BasicModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 项目名称：CardShare
 * 类描述：
 * 系统初始化部分
 * 创建人：Genesis
 * 创建时间：16/9/30 09:14
 * 修改人：Genesis
 * 修改时间：16/9/30 09:14
 * 修改备注：
 */

public abstract class LDModelFactory {
    private          HashMap<Integer, InitLoginModel> mLoginModel;
    protected static Context                          mContext;

    public LDModelFactory(Context context) {
        this.mContext = context;
    }

    protected void initData(String json) {
        if (mContext != null) {
            mLoginModel = new HashMap<>();
            try {
                JSONArray array = new JSONArray(json);
                if (array != null) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = (JSONObject) array.opt(i);
                        if (object != null) {
                            InitLoginModel model = new InitLoginModel();
                            model.setId(object.optInt("id"));
                            model.setModel(object.optString("model"));
                            mLoginModel.put(model.getId(), model);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<Integer, InitLoginModel> getLoginModel() {
        return mLoginModel;
    }

    public BasicModel getModel(int id) {
        InitLoginModel model = mLoginModel.get(id);
        BasicModel basicModel = null;
        if (model != null) {
            String className = model.getModel();
            if (className != null) {
                Class testTypeForName = null;
                try {
                    testTypeForName = Class.forName(className);
                    if (testTypeForName != null) {
                        basicModel = (BasicModel) testTypeForName.newInstance();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return basicModel;
    }

    public abstract void getLoginData();

    private class InitLoginModel {
        private int    id;
        private String model;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

}
