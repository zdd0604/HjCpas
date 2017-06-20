package com.hj.casps;

import com.alibaba.fastjson.JSONObject;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 鑫 Administrator on 2017/5/16.
 */

public class TestJson {
    @Test
    public void test1() {
        String str = "{\"dataList\": [{\"divId\": \"101\",\"divName\": \"小图\",\"parentdivId\": \"0\"},{\"divId\": \"102\",\"divName\": \"大图\",\"parentdivId\": \"0\"},{\"divId\": \"1013207\",\"divName\": \"蔬菜\",\"parentdivId\": \"101\"},{\"divId\": \"101535c\",\"divName\": \"水果\",\"parentdivId\": \"101\"},{\"divId\": \"10170a2\",\"divName\": \"肉类\",\"parentdivId\": \"101\"},{\"divId\": \"10163bf\",\"divName\": \"水产\",\"parentdivId\": \"101\"},{\"divId\": \"101c627\",\"divName\": \"米类\",\"parentdivId\": \"101\"},{\"divId\": \"1016edb\",\"divName\": \"面类\",\"parentdivId\": \"101\"},{\"divId\": \"101b590\",\"divName\": \"杂粮\",\"parentdivId\": \"101\"},{\"divId\": \"101b4bb\",\"divName\": \"食用油\",\"parentdivId\": \"101\"},{\"divId\": \"101d906\",\"divName\": \"调料\",\"parentdivId\": \"101\"},{\"divId\": \"101abc5\",\"divName\": \"蔬菜大图\",\"parentdivId\": \"101\"},{\"divId\": \"1028b83\",\"divName\": \"水果大图\",\"parentdivId\": \"102\"},{\"divId\": \"102e44b\",\"divName\": \"肉类大图\",\"parentdivId\": \"102\"},{\"divId\": \"102705a\",\"divName\": \"水产大图\",\"parentdivId\": \"102\"},{\"divId\": \"10297a6\",\"divName\": \"米类大图\",\"parentdivId\": \"102\"},{\"divId\": \"102c479\",\"divName\": \"面类大图\",\"parentdivId\": \"102\"},{\"divId\": \"1025979\",\"divName\": \"杂粮大图\",\"parentdivId\": \"102\"},{\"divId\": \"1029149\",\"divName\": \"食用油大图\",\"parentdivId\": \"102\"},{\"divId\": \"102ee80\",\"divName\": \"调料大图\",\"parentdivId\": \"102\"},{\"divId\": \"103\",\"divName\": \"其他图片\",\"parentdivId\": \"0\"},{\"divId\": \"1033fbb\",\"divName\": \"背景图片\",\"parentdivId\": \"103\"},{\"divId\": \"103d8ec\",\"divName\": \"证书、奖章\",\"parentdivId\": \"103\"}],\"return_code\": 0,\"return_message\": \"成功\"}";
        Entity entity = JSONObject.parseObject(str, Entity.class);
        List<DataListEntity> sourceList = entity.getDataList();
        List<DataTreeListEntity> treeList = getChild(sourceList, "0");
        for (int i = 0, size = treeList.size(); i < size; i++) {
            System.out.println(treeList.get(i));
        }

    }

    private List<DataTreeListEntity> getChild(List<DataListEntity> sourceList, String level) {
        List<DataTreeListEntity> treeList = new ArrayList<DataTreeListEntity>();
        for (DataListEntity item : sourceList) {
            if (level.equals(item.getParentdivId())) {
                DataTreeListEntity treeListEntity = new DataTreeListEntity(item);
                treeList.add(treeListEntity);
                String divId = treeListEntity.getEntity().getDivId();
                treeListEntity.setListEntities(getChild(sourceList, divId));

            }
        }
        return treeList;
    }

    public static class Entity {

        /**
         * listEntities : [{"divId":"101","divName":"小图","parentdivId":"0"},{"divId":"102","divName":"大图","parentdivId":"0"},{"divId":"1013207","divName":"蔬菜","parentdivId":"101"},{"divId":"101535c","divName":"水果","parentdivId":"101"},{"divId":"10170a2","divName":"肉类","parentdivId":"101"},{"divId":"10163bf","divName":"水产","parentdivId":"101"},{"divId":"101c627","divName":"米类","parentdivId":"101"},{"divId":"1016edb","divName":"面类","parentdivId":"101"},{"divId":"101b590","divName":"杂粮","parentdivId":"101"},{"divId":"101b4bb","divName":"食用油","parentdivId":"101"},{"divId":"101d906","divName":"调料","parentdivId":"101"},{"divId":"101abc5","divName":"蔬菜大图","parentdivId":"101"},{"divId":"1028b83","divName":"水果大图","parentdivId":"102"},{"divId":"102e44b","divName":"肉类大图","parentdivId":"102"},{"divId":"102705a","divName":"水产大图","parentdivId":"102"},{"divId":"10297a6","divName":"米类大图","parentdivId":"102"},{"divId":"102c479","divName":"面类大图","parentdivId":"102"},{"divId":"1025979","divName":"杂粮大图","parentdivId":"102"},{"divId":"1029149","divName":"食用油大图","parentdivId":"102"},{"divId":"102ee80","divName":"调料大图","parentdivId":"102"},{"divId":"103","divName":"其他图片","parentdivId":"0"},{"divId":"1033fbb","divName":"背景图片","parentdivId":"103"},{"divId":"103d8ec","divName":"证书、奖章","parentdivId":"103"}]
         * return_code : 0
         * return_message : 成功
         */

        private int return_code;
        private String return_message;
        /**
         * divId : 101
         * divName : 小图
         * parentdivId : 0
         */

        private List<DataListEntity> dataList;

        public int getReturn_code() {
            return return_code;
        }

        public void setReturn_code(int return_code) {
            this.return_code = return_code;
        }

        public String getReturn_message() {
            return return_message;
        }

        public void setReturn_message(String return_message) {
            this.return_message = return_message;
        }

        public List<DataListEntity> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListEntity> dataList) {
            this.dataList = dataList;
        }


    }

    public static class DataTreeListEntity {
        List<DataTreeListEntity> listEntities;
        DataListEntity entity;

        @Override
        public String toString() {
            return "DataTreeListEntity{" +
                    "listEntities=" + listEntities +
                    ", entity=" + entity +
                    '}';
        }

        public DataTreeListEntity(DataListEntity entity) {
            this.entity = entity;
        }

        public DataListEntity getEntity() {
            return entity;
        }

        public List<DataTreeListEntity> getListEntities() {
            return listEntities;
        }

        public void setListEntities(List<DataTreeListEntity> dataList) {
            this.listEntities = dataList;
        }
    }

    public static class DataListEntity {
        private String divId;
        private String divName;
        private String parentdivId;

        @Override
        public String toString() {
            return "DataListEntity{" +
                    "divId='" + divId + '\'' +
                    ", divName='" + divName + '\'' +
                    ", parentdivId='" + parentdivId + '\'' +
                    '}';
        }

        public String getDivId() {
            return divId;
        }

        public void setDivId(String divId) {
            this.divId = divId;
        }

        public String getDivName() {
            return divName;
        }

        public void setDivName(String divName) {
            this.divName = divName;
        }

        public String getParentdivId() {
            return parentdivId;
        }

        public void setParentdivId(String parentdivId) {
            this.parentdivId = parentdivId;
        }
    }
}

