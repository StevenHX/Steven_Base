package com.hx.stevenbase.ui.bingGallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WanBean implements Parcelable {
    private int offset;
    private int size;
    private int total;
    private int pageCount;
    private int curPage;
    private boolean over;
    private List<WanBean.DatasBean> datas;

    protected WanBean(Parcel in) {
        offset = in.readInt();
        size = in.readInt();
        total = in.readInt();
        pageCount = in.readInt();
        curPage = in.readInt();
        over = in.readByte() != 0;
    }

    public static final Creator<WanBean> CREATOR = new Creator<WanBean>() {
        @Override
        public WanBean createFromParcel(Parcel in) {
            return new WanBean(in);
        }

        @Override
        public WanBean[] newArray(int size) {
            return new WanBean[size];
        }
    };

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public List<WanBean.DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<WanBean.DatasBean> datas) {
        this.datas = datas;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(offset);
        parcel.writeInt(size);
        parcel.writeInt(total);
        parcel.writeInt(pageCount);
        parcel.writeInt(curPage);
        parcel.writeByte((byte) (over ? 1 : 0));
    }

    public  class DatasBean {
        /**
         * id : 1578
         * title : 这些 Drawable 的小技巧，你都了解吗？
         * chapterId : 168
         * chapterName : Drawable
         * envelopePic : null
         * link : https://juejin.im/post/5a28b2d0f265da431c703153
         * author :  承香墨影
         * origin : null
         * publishTime : 1512660849000
         * zan : null
         * desc : null
         * visible : 1
         * niceDate : 2017-12-07
         * courseId : 13
         * collect : false
         */

        private int id;
        private String title;
        private int chapterId;
        private String chapterName;
        private String envelopePic;
        private String link;
        private String author;
        private String origin;
        private long publishTime;
        private String zan;
        private String desc;
        private int visible;
        private String niceDate;
        private int courseId;
        private boolean collect;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }
    }
}
