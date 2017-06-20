package com.hj.casps.entity.appordergoods;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Admin on 2017/5/24.
 * 获取地域树
 */

public class GetTreeModalRespon<T>  implements Serializable {

    private static final long serialVersionUID = -5485119269615287890L;


    /**
     * area_tree : [{"areaCode":"110002","id":"1","nodes":[{"areaCode":"110101","id":"3","parentCode":"110002","sysCode":"100010111101","text":"东城区"},{"areaCode":"110102","id":"4","parentCode":"110002","sysCode":"100010111102","text":"西城区"}],"parentCode":"100","sysCode":"10001011","text":"北京市"},{"areaCode":"120000","id":"22","nodes":[{"areaCode":"120101","id":"24","parentCode":"120000","sysCode":"100010121101","text":"和平区"},{"areaCode":"120102","id":"25","parentCode":"120000","sysCode":"100010121102","text":"河东区"}],"parentCode":"100","sysCode":"10001012","text":"天津市"}]
     * return_code : 0
     * return_message : success
     */

   public int return_code;
   public String return_message;
   public T area_tree;
}
