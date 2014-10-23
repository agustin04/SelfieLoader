package com.sergio_agustin.selfieloader;


/**
 * Created by Sergio on 23/10/2014.
 */
public class InstagramData {
    //There's much more data but we're using only the images.
    private Image lowRes,stdRes,thumb;

    public InstagramData() {
        this.lowRes = null;
        this.stdRes = null;
        this.thumb = null;
    }

    public InstagramData(Image lowRes, Image stdRes, Image thumb) {
        this.lowRes = lowRes;
        this.stdRes = stdRes;
        this.thumb = thumb;
    }

    public Image getLowRes() {
        return lowRes;
    }

    public void setLowRes(Image lowRes) {
        this.lowRes = lowRes;
    }

    public Image getStdRes() {
        return stdRes;
    }

    public void setStdRes(Image stdRes) {
        this.stdRes = stdRes;
    }

    public Image getThumb() {
        return thumb;
    }

    public void setThumb(Image thumb) {
        this.thumb = thumb;
    }
}
