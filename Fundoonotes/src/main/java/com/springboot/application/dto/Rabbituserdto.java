package com.springboot.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rabbituserdto {

	@JsonProperty("supplierId")
    private String supplierId;
	
	@JsonProperty("supplierName") 
    private String supplierName;
	
	@JsonProperty("supplierUrl")
	private String supplierUrl;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierUrl() {
		return supplierUrl;
	}

	public void setSupplierUrl(String supplierUrl) {
		this.supplierUrl = supplierUrl;
	}

	@Override
	public String toString() {
		return "Userdetails [supplierId=" + supplierId + ", supplierName=" + supplierName + ", supplierUrl="
				+ supplierUrl + "]";
	}
	
}
