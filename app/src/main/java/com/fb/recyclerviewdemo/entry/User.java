package com.fb.recyclerviewdemo.entry;

import java.util.List;

/**
 * Description:
 * CreateTime: 2018/6/15 13:50
 * Author: ShengDecheng
 */

public class User {

    /**
     * data : {"collectIds":[2831,2830,2826,2824,2819,2816,2813,2809,2806,2795,2776,2774,2775,2733,2727,2707,2702,2701,2490,2691,2687,2497,2492,1766,1773,1660,1670,2483,2482,2452,2439,2440,2424,2414,2253,2884,2886,2883,2881,2872,2868,2837,2836],"email":"524100248@qq.com","icon":"","id":5348,"password":"chenfeng2016","type":0,"username":"524100248@qq.com"}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * collectIds : [2831,2830,2826,2824,2819,2816,2813,2809,2806,2795,2776,2774,2775,2733,2727,2707,2702,2701,2490,2691,2687,2497,2492,1766,1773,1660,1670,2483,2482,2452,2439,2440,2424,2414,2253,2884,2886,2883,2881,2872,2868,2837,2836]
         * email : 524100248@qq.com
         * icon :
         * id : 5348
         * password : chenfeng2016
         * type : 0
         * username : 524100248@qq.com
         */

        private String email;
        private String icon;
        private int id;
        private String password;
        private int type;
        private String username;
        private List<Integer> collectIds;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<Integer> getCollectIds() {
            return collectIds;
        }

        public void setCollectIds(List<Integer> collectIds) {
            this.collectIds = collectIds;
        }
    }
}
