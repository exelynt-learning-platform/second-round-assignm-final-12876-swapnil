package com.multigenesys.ecommerce_service.dto;

public class OrderRequest {

    private String shippingAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    
	public OrderRequest() {
		super();
	}

	public OrderRequest(String shippingAddress, String city, String state, String zipCode, String country) {
		super();
		this.shippingAddress = shippingAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
    


}