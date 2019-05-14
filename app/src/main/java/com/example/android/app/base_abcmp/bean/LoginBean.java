package com.example.android.app.base_abcmp.bean;

import java.util.List;

public class LoginBean {

    /**
     * status : 1
     * msg : success
     * data : [{"id":"12","title":"挖机"},{"id":"13","title":"破碎锤"},{"id":"14","title":"压路机"},{"id":"15","title":"装载机"},{"id":"17","title":"挖机01"},{"id":"18","title":"挖机001"},{"id":"19","title":"破碎锤01"},{"id":"20","title":"挖机0001"},{"id":"21","title":"挖机02"},{"id":"22","title":"挖机03"},{"id":"23","title":"挖机04"}]
     */

    private String status;
    private String msg;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * title : 挖机
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
