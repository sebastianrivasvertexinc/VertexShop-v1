package com.salesmanager.core.business.services.tax.vertex;

public class Taxis{
    public double calculatedTax;
    public CalculationRuleId calculationRuleId;

    public double getEffectiveRate() {
        return effectiveRate;
    }

    public void setEffectiveRate(double effectiveRate) {
        this.effectiveRate = effectiveRate;
    }

    public double effectiveRate;
    public double exempt;
    public Imposition imposition;
    public ImpositionType impositionType;
    public InclusionRuleId inclusionRuleId;
    public boolean isService;
    public Jurisdiction jurisdiction;
    public boolean maxTaxIndicator;
    public double nominalRate;
    public double nonTaxable;
    public boolean notRegisteredIndicator;
    public String situs;
    public String taxCollectedFromParty;
    public String taxResult;
    public String taxStructure;
    public String taxType;
    public double taxable;
}
