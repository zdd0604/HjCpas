package com.hj.casps.commodity;

import com.hj.casps.entity.goodsmanager.SelectPicture02ListEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27.
 */

public class ImageData {

        private List<SelectPicture02ListEntity> list;
        private SelectPicture02ListEntity entity;

        public SelectPicture02ListEntity getEntity() {
            return entity;
        }

        public void setEntity(SelectPicture02ListEntity entity) {
            this.entity = entity;
        }

        public List<SelectPicture02ListEntity> getList() {
            return list;
        }

        public void setList(List<SelectPicture02ListEntity> list) {
            this.list = list;
        }

}
