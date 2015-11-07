package com.skip.entity.shop;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.skip.entity.product.Product;
import com.skip.entity.purchases.Purchases;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;


/**
 * The persistent class for the shop database table.
 * 
 */
@Entity
@Table(name="shop")
@NamedQueries({
    @NamedQuery(name = "Shop.findAll", query = "SELECT s FROM Shop s"),
    @NamedQuery(name = "Shop.findByShopId", query = "SELECT s FROM Shop s WHERE s.shopid = :shopid"),
    @NamedQuery(name = "Shop.findByName", query = "SELECT s FROM Shop s WHERE s.name = :name"),
    @NamedQuery(name = "Shop.findByAddress", query = "SELECT s FROM Shop s WHERE s.address = :address"),
    @NamedQuery(name = "Shop.findByApikey", query = "SELECT s FROM Shop s WHERE s.apikey = :apikey"),
    @NamedQuery(name = "Shop.findBySecret", query = "SELECT s FROM Shop s WHERE s.secret = :secret"),
    @NamedQuery(name = "Shop.findByAccessURL", query = "SELECT s FROM Shop s WHERE s.accessURL = :accessURL"),
    @NamedQuery(name = "Shop.findByLatitude", query = "SELECT s FROM Shop s WHERE s.latitude = :latitude"),
    @NamedQuery(name = "Shop.findByLongitude", query = "SELECT s FROM Shop s WHERE s.longitude = :longitude")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="shop")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long shopid;

	@Column(length=200)
	private String accessURL;

	@Column(nullable=false, length=100)
	private String address;

	@Column(length=60)
	private String apikey;

	
	private BigDecimal latitude;

	
	private BigDecimal longitude;

	@Column(nullable=false, length=45)
	private String name;

	@Column(length=45)
	private String secret;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="shop")
	private List<Product> products;

	//bi-directional many-to-one association to Purchas
	@OneToMany(mappedBy="shop")
	private List<Purchases> purchases;

	public Shop() {
	}

	public Long getShopid() {
		return this.shopid;
	}

	public void setShopid(Long shopId) {
		this.shopid = shopId;
	}

	public String getAccessURL() {
		return this.accessURL;
	}

	public void setAccessURL(String accessURL) {
		this.accessURL = accessURL;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getApikey() {
		return this.apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return this.secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setShop(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setShop(null);

		return product;
	}

	public List<Purchases> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchases> purchases) {
		this.purchases = purchases;
	}

	public Purchases addPurchas(Purchases purchas) {
		getPurchases().add(purchas);
		purchas.setShop(this);

		return purchas;
	}

	public Purchases removePurchas(Purchases purchas) {
		getPurchases().remove(purchas);
		purchas.setShop(null);

		return purchas;
	}
	
	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (shopid != null ? shopid.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Shop)) {
	            return false;
	        }
	        Shop other = (Shop) object;
	        if ((this.shopid == null && other.shopid != null) || (this.shopid != null && !this.shopid.equals(other.shopid))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.skip.entity.Shop[ shopId=" + shopid + " ]";
	    }

}