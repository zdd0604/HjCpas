package com.hj.casps.entity.appsettle;

import java.util.List;

/**
 * 获取地域树
 */
public class AreaTreeBean {
    /**
     * areaCode : 110002
     * id : 1
     * nodes : [{"areaCode":"110101","id":"3","parentCode":"110002","sysCode":"100010111101","text":"东城区"},{"areaCode":"110102","id":"4","parentCode":"110002","sysCode":"100010111102","text":"西城区"}]
     * parentCode : 100
     * sysCode : 10001011
     * text : 北京市
     */

    private String areaCode;
    private String id;
    private String parentCode;
    private String sysCode;
    private String text;
    private List<NodesBean> nodes;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<NodesBean> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodesBean> nodes) {
        this.nodes = nodes;
    }

    public static class NodesBean {
        /**
         * areaCode : 110101
         * id : 3
         * parentCode : 110002
         * sysCode : 100010111101
         * text : 东城区
         */

        private String areaCode;
        private String id;
        private String parentCode;
        private String sysCode;
        private String text;

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getSysCode() {
            return sysCode;
        }

        public void setSysCode(String sysCode) {
            this.sysCode = sysCode;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}