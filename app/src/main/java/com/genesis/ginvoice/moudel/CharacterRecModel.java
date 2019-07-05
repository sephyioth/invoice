package com.genesis.ginvoice.moudel;

import android.text.TextUtils;

import com.genesis.ginvoice.beans.CharacterLineBean;
import com.genesis.ginvoice.beans.LocationBean;
import com.genesis.ginvoice.beans.OCRResultBean;
import com.genesis.ginvoice.beans.SingleCharBean;
import com.genesis.ginvoice.beans.VertexesLocationsBean;
import com.sephyioth.ldcore.model.BasicModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * for example :
 * {"log_id": 4246333065270113483, "direction": 0, "words_result_num": 1, "words_result": [{
 * "vertexes_location": [{"y": 124, "x": 18}, {"y": 124, "x": 632}, {"y": 211, "x": 632}, {"y":
 * 211, "x": 18}], "chars": [{"char": "成", "location": {"width": 54, "top": 135, "left": 40,
 * "height": 69}}, {"char": "熟", "location": {"width": 53, "top": 135, "left": 107, "height": 69}
 * }, {"char": "团", "location": {"width": 54, "top": 135, "left": 173, "height": 69}}, {"char":
 * "队", "location": {"width": 54, "top": 135, "left": 239, "height": 69}}, {"char": "的",
 * "location": {"width": 54, "top": 135, "left": 305, "height": 69}}, {"char": "开", "location": {
 * "width": 54, "top": 135, "left": 373, "height": 69}}, {"char": "发", "location": {"width": 54,
 * "top": 135, "left": 439, "height": 69}}, {"char": "方", "location": {"width": 54, "top": 135,
 * "left": 505, "height": 69}}, {"char": "式", "location": {"width": 54, "top": 135, "left": 571,
 * "height": 69}}], "min_finegrained_vertexes_location": [{"y": 124, "x": 18}, {"y": 124, "x":
 * 631}, {"y": 211, "x": 631}, {"y": 211, "x": 18}], "finegrained_vertexes_location": [{"y": 124,
 * "x": 18}, {"y": 124, "x": 86}, {"y": 124, "x": 154}, {"y": 124, "x": 222}, {"y": 124, "x": 290
 * }, {"y": 124, "x": 358}, {"y": 124, "x": 426}, {"y": 124, "x": 494}, {"y": 124, "x": 562}, {"y
 * ": 124, "x": 630}, {"y": 124, "x": 632}, {"y": 158, "x": 632}, {"y": 192, "x": 632}, {"y":
 * 211, "x": 632}, {"y": 211, "x": 564}, {"y": 211, "x": 496}, {"y": 211, "x": 428}, {"y": 211,
 * "x": 360}, {"y": 211, "x": 292}, {"y": 211, "x": 224}, {"y": 211, "x": 156}, {"y": 211, "x":
 * 88}, {"y": 211, "x": 20}, {"y": 211, "x": 18}, {"y": 177, "x": 18}, {"y": 143, "x": 18}],
 * "location": {"width": 617, "top": 124, "left": 18, "height": 90}, "words": "成熟团队的开发方式"}]}
 * <p>
 * 创建人：genesis
 * 创建时间：2019-06-11 21:09
 * 修改人：genesis
 * 修改时间：2019-06-11 21:09
 * 修改备注：
 */
public class CharacterRecModel extends BasicModel {
    private static final String TYPE_LOG_ID                  = "log_id";
    private static final String TYPE_DIRECTION               = "direction";
    private static final String TYPE_WORDS_RESULT_NUM        = "words_result_num";
    private static final String TYPE_WORDS_RESULT            = "words_result";
    private static final String TYPE_VERTEXES_LOCATION       = "vertexes_location";
    private static final String TYPE_CHARS                   = "chars";
    private static final String TYPE_WORDS                   = "words";
    private static final String TYPE_FINEGRAINED_LOCATION    = "finegrained_vertexes_location";
    private static final String TYPE_MINFINEGRAINED_LOCATION = "min_finegrained_vertexes_location";
    private static final String TYPE_WORDS_LOCATION          = "location";

    // every
    private static final String TYPE_ITEM_CHAR         = "char";
    private static final String TYPE_ITEM_LOCAL        = "location";
    private static final String TYPE_ITEM_LOCAL_X      = "x";
    private static final String TYPE_ITEM_LOCAL_Y      = "y";
    private static final String TYPE_ITEM_LOCAL_WIDTH  = "width";
    private static final String TYPE_ITEM_LOCAL_HEIGHT = "height";
    private static final String TYPE_ITEM_LOCAL_TOP    = "top";
    private static final String TYPE_ITEM_LOCAL_LEFT   = "left";

    private ArrayList<OCRResultBean> mBeans;

    public CharacterRecModel () {
        this.mBeans = new ArrayList<>();
    }

    @Override
    public Object parse (String json) {
        super.parse(json);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        OCRResultBean resultBean = new OCRResultBean();
        try {
            JSONObject object = new JSONObject(json);
            resultBean.setLogId(object.optLong(TYPE_LOG_ID));
            resultBean.setDirection(object.optLong(TYPE_DIRECTION));
            resultBean.setWordsResultNum(object.optInt(TYPE_WORDS_RESULT_NUM));
            JSONArray resultObj = object.optJSONArray(TYPE_WORDS_RESULT);
            for (int i = 0; i < resultObj.length(); i++) {
                CharacterLineBean bean = new CharacterLineBean();
                JSONArray verLoactionArray =
                        resultObj.optJSONObject(i).optJSONArray(TYPE_VERTEXES_LOCATION);
                bean.setVertexesLocations(parseVertexesLocations(verLoactionArray));
                JSONArray charsArray = resultObj.optJSONObject(i).optJSONArray(TYPE_CHARS);
                bean.setSingleChars(parseSingleChar(charsArray));
                JSONArray finLocations =
                        resultObj.optJSONObject(i).optJSONArray(TYPE_FINEGRAINED_LOCATION);
                bean.setFinegrainedVerLocations(parseVertexesLocations(finLocations));
                JSONArray minFinLocations =
                        resultObj.optJSONObject(i).optJSONArray(TYPE_MINFINEGRAINED_LOCATION);
                bean.setMinFinegraineLocations(parseVertexesLocations(minFinLocations));
                bean.setWordLocation(parseLocation(resultObj.optJSONObject(i).optJSONObject(TYPE_WORDS_LOCATION)));
                bean.setWords(resultObj.optJSONObject(i).optString(TYPE_WORDS));
                resultBean.addCharacterLine(bean);
            }
            mBeans.add(resultBean);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return resultBean;
    }

    private ArrayList<SingleCharBean> parseSingleChar (JSONArray jsonArray) {
        ArrayList<SingleCharBean> beans = new ArrayList<>();
        try {
            if (jsonArray == null) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                SingleCharBean bean = new SingleCharBean();
                bean.setSingleChar(object.optString(TYPE_ITEM_CHAR));
                bean.setLocation(parseLocation(object.optJSONObject(TYPE_ITEM_LOCAL)));
                beans.add(bean);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return beans;
    }

    private LocationBean parseLocation (JSONObject object) {
        if (object == null) {
            return null;
        }
        LocationBean bean = new LocationBean();
        try {
            bean.setWidth(object.optInt(TYPE_ITEM_LOCAL_WIDTH));
            bean.setHeight(object.optInt(TYPE_ITEM_LOCAL_HEIGHT));
            bean.setLeft(object.optInt(TYPE_ITEM_LOCAL_LEFT));
            bean.setTop(object.optInt(TYPE_ITEM_LOCAL_TOP));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private ArrayList<LocationBean> parseLocations (String optString) {
        if (optString == null) {
            return null;
        }
        ArrayList<LocationBean> beans = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(optString);
            if (array == null) {
                return null;
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                LocationBean bean = parseLocation(object);
                beans.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return beans;
    }

    private ArrayList<VertexesLocationsBean> parseVertexesLocations (JSONArray jsonArray) throws JSONException {
        ArrayList<VertexesLocationsBean> beans = new ArrayList<>();
        try {
            if (jsonArray == null) {
                return null;
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                VertexesLocationsBean bean = new VertexesLocationsBean();
                bean.setX(object.optInt(TYPE_ITEM_LOCAL_X));
                bean.setY(object.optInt(TYPE_ITEM_LOCAL_Y));
                beans.add(bean);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public void clear () {
        mBeans.clear();
    }

    @Override
    public String toString () {
        StringBuffer string = new StringBuffer();
        for (OCRResultBean bean : mBeans) {
            string.append(bean.getWords());
            string.append("\t\t");
        }
        return string.toString();
    }

    public String getWords () {
        return this.toString();
    }
}

