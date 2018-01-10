package at.htl.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = "Products.getAll", query = "select p from Products p"),
        @NamedQuery(name = "Products.getProductBySku", query = "select q from Products q where q.sku = :sku"),
        @NamedQuery(name = "Products.NoCustomDescription", query = "select p from Products p where p.customDescription = false"),
        @NamedQuery(name = "Products.UpdatePriceStock", query = "update Products SET price = :price, inStock = :inStock where sku = :sku"),
        @NamedQuery(name = "Products.GetMannolProducts", query = "select p from Products p where UPPER(p.title) LIKE '%MANNOL%' and p.customDescription = false"),
        @NamedQuery(name = "Products.GetProductsByBrand", query = "select p from Products p where UPPER(p.brand) LIKE :brand")
}  )

@Table(name="Products")
@Entity
public class Products
{
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/

    //Bild
    //@Column(name = "BASEIMAGE")
    private String baseImage;

    //SmallBild
    //@Column(name = 'SMALLIMAGE')
    private String smallImage;

    //Beschreibung
    @Column(/*name = "DESCRIPTION", */length = 100000)
    @Lob
    private String description;

    //@Column(/*name = "DESCRIPTION", */length = 200)
    //@Lob
    //private String metaDescription;

    //Marke & Model
    //@Column(name = "BRAND")
    private String brand;

    //Titel des Produktes
    //@Column(name = "TITLE")
    private String title;

    @Column(/*name = "DESCRIPTION", */length = 10000)
    @Lob
    private String related;

    private String deliveryTime;

    //Verfügbarkeit
    //@Column(name = "INSTOCK")
    private int inStock;

    //Preis
    //@Column(name = "PRICE")
    private double price;

    //Sku
    @NotNull
    @Id
    //@Column(name = "SKU")
    private String sku;

    //Container
    //@Column(name = "CONTAINER")
    private double container;

    //@Column(name ="METATITLE")
    private String metaTitle;

    //@Column(name ="CUSTOMDESCRIPTION")
    private boolean customDescription;

    private int orderProcessingTime;

    @Override
    public String toString() {
        return  "^"+ sku +"^;" + "^1^;" + "^4^;" + "^1^;" + "^0^;" + "^base^;" + "^simple^;" + "^Default^;" +"^"+ "-"+"^" + ";" + "^"+ description + "^"+ ";" + "^"+ metaTitle + "^"+ ";" + "^"+ inStock + "^"+ ";" + "^"+ price +"^;^"+ baseImage +"^;^" + baseImage +"^;^" + baseImage +"^;^" + container + "^;^" + brand + "^\n";
    }
    public String toStringWithoutPicturesImport() {

        //sku;tax_class_id;visibility;status;weight;_product_website;_type;_attribute_set;name;qty;price;weight;manufacturer;description;cjm_ships_in
        return  "^"+ sku +"^;" + "^1^;" + "^4^;" + "^1^;" + "^0^;" + "^base^;" + "^simple^;" + "^Default^;" + "^"+ metaTitle + "^"+ ";" + "^"+ inStock + "^"+ ";" + "^"+ price +"^;^"/*+ baseImage +"^;^" + baseImage +"^;^" + baseImage +"^;^"*/ + container + "^;^" + brand +"^" + ";^"+description+";^"+orderProcessingTime+"^\n";
    }
    public String toStringFullExport() {

        //sku;tax_class_id;visibility;status;weight;_product_website;_type;_attribute_set;name;qty;price;image;small_image;thumbnail;weight;manufacturer;description;cjm_ships_in
        return  "^"+ sku +"^;" + "^1^;" + "^4^;" + "^1^;" + "^0^;" + "^base^;" + "^simple^;" + "^Default^;" + "^"+ metaTitle + "^"+ ";" + "^"+ inStock + "^"+ ";" + "^"+ price +"^;^"+ baseImage +"^;^" + baseImage +"^;^" + baseImage +"^;^" + container + "^;^" + brand +"^" + ";^"+description+";^"+orderProcessingTime+"^\n";
    }

    public String toStringRelatedExport() {
        //sku;re_skus
        return "^"+getSku()+"^;^"+getRelated()+"^\n";
    }

    public String toStringStockExport() {
        //sku;qty;price
        return "^"+getSku()+"^;^"+getInStock()+"^;^"+getPrice()+"^\n";
    }



//region Getter & Setter


    public int getOrderProcessingTime() {
        return orderProcessingTime;
    }

    public void setOrderProcessingTime(int orderProcessingTime) {
        this.orderProcessingTime = orderProcessingTime;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public boolean isCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(boolean _customDescription) {
        this.customDescription = _customDescription;
    }

    //public String getMetaDescription() {
    //    return metaDescription;
    //}

    //public void setMetaDescription(String metaDescription) {
    //    this.metaDescription = metaDescription;
    //}

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String _smallImage) {
        this.smallImage = _smallImage;
    }

    public double getContainer() {
        return container;
    }

    public void setContainer(double _container) {
        this.container = _container;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String _sku) {
        this.sku = _sku;
    }

    public String getBaseImage() { return baseImage; }

    public void setBaseImage(String _picture) { this.baseImage = _picture;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title) {
        this.title = _title;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int _inStock) {
        this.inStock = _inStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double _price) {
        this.price = _price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String _brand) {
        this.brand = _brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String _metaTitle) {
        this.metaTitle = _metaTitle;
    }




    //endregion
}
