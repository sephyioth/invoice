package com.genesis.ginvoice.beans;

import java.util.ArrayList;

/**
 * 项目名称：CardShare
 * 类描述：
 * 创建人：genesis
 * 创建时间：2019-07-02 14:54
 * 修改人：genesis
 * 修改时间：2019-07-02 14:54
 * 修改备注：
 */
public class CharacterLineBean {

    private String                           wordsResult;
    private String                           words;
    private LocationBean                     wordLocation;
    private ArrayList<VertexesLocationsBean> vertexesLocations;
    private ArrayList<SingleCharBean>        singleChars;
    private ArrayList<VertexesLocationsBean> minFinegraineLocations;
    private ArrayList<VertexesLocationsBean> finegrainedVerLocations;


    public String getWordsResult () {
        return wordsResult;
    }

    public void setWordsResult (String wordsResult) {
        this.wordsResult = wordsResult;
    }

    public ArrayList<VertexesLocationsBean> getVertexesLocations () {
        return vertexesLocations;
    }

    public void setVertexesLocations (ArrayList<VertexesLocationsBean> vertexesLocations) {
        this.vertexesLocations = vertexesLocations;
    }

    public ArrayList<SingleCharBean> getSingleChars () {
        return singleChars;
    }

    public void setSingleChars (ArrayList<SingleCharBean> singleChars) {
        this.singleChars = singleChars;
    }


    public ArrayList<VertexesLocationsBean> getMinFinegraineLocations () {
        return minFinegraineLocations;
    }

    public void setMinFinegraineLocations (ArrayList<VertexesLocationsBean> minFinegraineLocations) {
        this.minFinegraineLocations = minFinegraineLocations;
    }

    public ArrayList<VertexesLocationsBean> getFinegrainedVerLocations () {
        return finegrainedVerLocations;
    }

    public void setFinegrainedVerLocations (ArrayList<VertexesLocationsBean> finegrainedVerLocations) {
        this.finegrainedVerLocations = finegrainedVerLocations;
    }

    public String getWords () {
        return words;
    }

    public void setWords (String words) {
        this.words = words;
    }

    public LocationBean getWordLocation () {
        return wordLocation;
    }

    public void setWordLocation (LocationBean wordLocation) {
        this.wordLocation = wordLocation;
    }
}
