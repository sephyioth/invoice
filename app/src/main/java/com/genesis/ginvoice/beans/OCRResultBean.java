package com.genesis.ginvoice.beans;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-06-12 14:20
 * 修改人：genesis
 * 修改时间：2019-06-12 14:20
 * 修改备注：
 */
public class OCRResultBean {

    private long                         logId;
    private long                         direction;
    private int                          wordsResultNum;
    private ArrayList<CharacterLineBean> characterLineBeans;

    public OCRResultBean () {
        characterLineBeans = new ArrayList<>();
    }

    public long getLogId () {
        return logId;
    }

    public void setLogId (long logId) {
        this.logId = logId;
    }

    public long getDirection () {
        return direction;
    }

    public void setDirection (long direction) {
        this.direction = direction;
    }

    public int getWordsResultNum () {
        return wordsResultNum;
    }

    public void setWordsResultNum (int wordsResultNum) {
        this.wordsResultNum = wordsResultNum;
    }

    public ArrayList<CharacterLineBean> getCharacterLineBeans () {
        return characterLineBeans;
    }

    public void addCharacterLine (CharacterLineBean bean) {
        this.characterLineBeans.add(bean);
    }

    public void clear () {
        this.characterLineBeans.clear();
    }

    public String getWords () {
        StringBuffer stringBuffer = new StringBuffer();
        LocationBean lastLocation = null;
        for (CharacterLineBean bean : characterLineBeans) {
            LocationBean thisLocation = bean.getWordLocation();
            if (thisLocation == null || lastLocation == null) {
                stringBuffer.append(bean.getWords());
                lastLocation = thisLocation;
                continue;
            }
            if ((thisLocation.getTop() > lastLocation.getTop() + lastLocation.getHeight() / 2) || (thisLocation.getLeft() < lastLocation.getLeft() + lastLocation.getWidth() / 2)) {
                stringBuffer.append("\n");
            } else {
                stringBuffer.append("\t\t");
            }
            stringBuffer.append(bean.getWords());
            lastLocation = thisLocation;
        }
        return stringBuffer.toString();
    }
}
