package com.salesmanager.core.business.services.tax.vertex;
import java.util.ArrayList;
public class Data{
    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer customer;
    public String documentDate;
    public String documentNumber;
    public ArrayList<LineItem> lineItems;
    public boolean returnAssistedParametersIndicator;
    public boolean roundAtLineLevel;
    public String saleMessageType;
    public Seller seller;

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double subTotal;
    public double total;
    public double totalTax;
    public String transactionType;
}
