package com.skip.entity.product;

import java.io.Serializable;

import javax.persistence.*;

import com.skip.entity.purchasedetails.Purchasedetails;
import com.skip.entity.shop.Shop;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findByProductId", query = "SELECT p FROM Product p WHERE p.productid = :productid"),
    @NamedQuery(name = "Product.findByName", query = "SELECT p FROM Product p WHERE p.name = :name"),
    @NamedQuery(name = "Product.findByDescription", query = "SELECT p FROM Product p WHERE p.description = :description"),
    @NamedQuery(name = "Product.findByBarcode", query = "SELECT p FROM Product p WHERE p.barcode = :barcode"),
    @NamedQuery(name = "Product.findByUnitPrice", query = "SELECT p FROM Product p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "Product.findByType", query = "SELECT p FROM Product p WHERE p.type = :type"),
    @NamedQuery(name = "Product.findByManufacturer", query = "SELECT p FROM Product p WHERE p.manufacturer = :manufacturer")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long productid;

	@Column(nullable=false, length=30)
	private String barcode;

	@Column(length=100)
	private String description;

	@Column(length=45)
	private String manufacturer;

	@Column(nullable=false, length=50)
	private String name;

	@Column(length=30)
	private String type;

	@Column(nullable=false, precision=10, scale=4)
	private BigDecimal unitPrice;

	//bi-directional many-to-one association to Shop
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="shopId", nullable=false)
	private Shop shop;

	//bi-directional many-to-one association to Purchasedetail
	@OneToMany(mappedBy="product")
	private List<Purchasedetails> purchasedetails;

	public Product() {
	}

	public Long getProductId() {
		return this.productid;
	}

	public void setProductId(Long productId) {
		this.productid = productId;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	private java.lang.Long shopid;
	 @Column(name = "shopId" , nullable = false )
	   public java.lang.Long getShopid() {
	      return shopid;
	   }

	   /**
	    * Sets the value of the <code>shopid</code> property.
	    *
	    * @param shopid the value for the <code>shopid</code> property
	    */
	   public void setShopid(java.lang.Long shopid) {
	      this.shopid = shopid;
	   }

	public List<Purchasedetails> getPurchasedetails() {
		return this.purchasedetails;
	}

	public void setPurchasedetails(List<Purchasedetails> purchasedetails) {
		this.purchasedetails = purchasedetails;
	}

	public Purchasedetails addPurchasedetail(Purchasedetails purchasedetail) {
		getPurchasedetails().add(purchasedetail);
		purchasedetail.setProduct(this);

		return purchasedetail;
	}

	public Purchasedetails removePurchasedetail(Purchasedetails purchasedetail) {
		getPurchasedetails().remove(purchasedetail);
		purchasedetail.setProduct(null);

		return purchasedetail;
	}
	
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (productid != null ? productid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productid == null && other.productid != null) || (this.productid != null && !this.productid.equals(other.productid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.skip.entity.Product[ productId=" + productid + " ]";
    }

}