package site.nebulas.ui.ad.splash.model;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/7/19
 */
public class AdModel {

    private int adId;
    // 照片url
    private String imgUrl;

    public AdModel() {
    }

    public AdModel(int adId, String imgUrl) {
        this.adId = adId;
        this.imgUrl = imgUrl;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
