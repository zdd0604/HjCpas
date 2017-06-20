package com.hj.casps.entity.goodsmanager.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class NoteEntity implements Serializable{


    private static final long serialVersionUID = -7140695310421056810L;
    /**
     * categoryId : 1002
     * categoryName : 副食品
     * nodes : [{"categoryId":"1002005","categoryName":"水产","nodes":[{"categoryId":"1002005003","categoryName":"水产干品","nodes":[]},{"categoryId":"1002005001","categoryName":"水产鲜品","nodes":[]},{"categoryId":"1002005002","categoryName":"水产冻品","nodes":[]}]},{"categoryId":"1002004","categoryName":"禽类","nodes":[{"categoryId":"1002004001","categoryName":"禽类鲜品","nodes":[]}]},{"categoryId":"1002003","categoryName":"畜类","nodes":[{"categoryId":"1002003002","categoryName":"畜类冻品","nodes":[]},{"categoryId":"1002003001","categoryName":"畜类鲜品","nodes":[]}]},{"categoryId":"1002001","categoryName":"蔬菜","nodes":[{"categoryId":"1002001001","categoryName":"叶花类菜","nodes":[]},{"categoryId":"1002001003","categoryName":"根茎类菜","nodes":[]}]},{"categoryId":"1002002","categoryName":"果品","nodes":[{"categoryId":"1002002001","categoryName":"鲜果","nodes":[]}]}]
     */

    private String categoryId;
    private String categoryName;
    private List<NodesBeanX> nodes;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<NodesBeanX> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesBeanX> nodes) {
        this.nodes = nodes;
    }

    public static class NodesBeanX {
        /**
         * categoryId : 1002005
         * categoryName : 水产
         * nodes : [{"categoryId":"1002005003","categoryName":"水产干品","nodes":[]},{"categoryId":"1002005001","categoryName":"水产鲜品","nodes":[]},{"categoryId":"1002005002","categoryName":"水产冻品","nodes":[]}]
         */

        private String categoryId;
        private String categoryName;
        private List<NodesBean> nodes;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<NodesBean> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBean> nodes) {
            this.nodes = nodes;
        }

        public static class NodesBean {
            /**
             * categoryId : 1002005003
             * categoryName : 水产干品
             * nodes : []
             */
            private String categoryId;
            private String categoryName;
            private List<?> nodes;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public List<?> getNodes() {
                return nodes;
            }

            public void setNodes(List<?> nodes) {
                this.nodes = nodes;
            }
        }
    }
}
