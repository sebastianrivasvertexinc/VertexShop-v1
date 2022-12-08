package com.salesmanager.core.business.services.tax.vertex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class VtxTaxCalcReq{

    public String getSaleMessageType() {
        if (saleMessageType==null)
            return "QUOTATION";
        else
        return this.saleMessageType;
    }

    public void setSaleMessageType(String saleMessageType) {
        this.saleMessageType = saleMessageType; }
   private String saleMessageType;

    public Seller getSeller() {
        return this.seller; }
    public void setSeller(Seller seller) {
        this.seller = seller; }
    private Seller seller;

    public Customer getCustomer() {
        return this.customer; }
    public void setCustomer(Customer customer) {
        this.customer = customer; }
    private Customer customer;

    public ArrayList<LineItem> getLineItems() {
        return this.lineItems; }
    public void setLineItems(ArrayList<LineItem> lineItems) {
        this.lineItems = lineItems; }
    ArrayList<LineItem> lineItems;

    public String getDocumentNumber() {
        return this.documentNumber; }
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber; }
    String documentNumber;

    public String getDocumentDate() {
        if (this.documentDate==null) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cal.getTime());
        }
        return this.documentDate; }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate; }
    String documentDate;

    public String getTransactionType() {
        return this.transactionType; }
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType; }
    String transactionType;
}


