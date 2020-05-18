package com.hx.steven.wxShare;

public class WxShareBean {

    /**
     * imgUrl : 主图
     * title : 标题
     * groupTitle : 朋友圈标题
     * link : 跳转url
     * desc : 分享描述
     * shareType : 0弹菜单|1微信好友|2微信朋友圈
     * shareMedia : 0网页|1 图片url|2 H5Base64图片|3 小程序
     */

    private String imgUrl;
    private String title;
    private String groupTitle;
    private String link;
    private String desc;
    private int shareType;
    private int shareMedia;
    private int mType;
    private String path;
    private String userName;



    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public int getShareMedia() {
        return shareMedia;
    }

    public void setShareMedia(int shareMedia) {
        this.shareMedia = shareMedia;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
