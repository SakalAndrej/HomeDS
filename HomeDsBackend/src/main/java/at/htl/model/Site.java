package at.htl.model;

public class Site {

    private String _linkToProduct;
    private String _titleOfProduct;

    //region Getter & Setter
    public void set_linkToAd(String _linkToAd) {
        this._linkToProduct = _linkToAd;
    }
    public void set_titleOfAdd(String _titleOfAdd) {
        this._titleOfProduct = _titleOfAdd.replace("%", "");
    }
    public String get_linkToProduct() {
        return _linkToProduct;
    }
    public String get_titleOfProduct() {
        return _titleOfProduct.replace("%", "");
    }
    //endregion

    public Site(String _linkToAd, String title) {
        this._linkToProduct = _linkToAd;
        this._titleOfProduct = title;
    }
}
