package com.salesmanager.core.business.services.tax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.system.MerchantConfigurationService;
import com.salesmanager.core.business.services.tax.vertex.VtxTaxCalc;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderSummary;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.reference.zone.Zone;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.system.MerchantConfiguration;
import com.salesmanager.core.model.tax.TaxConfiguration;
import com.salesmanager.core.model.tax.TaxItem;
import com.salesmanager.core.model.tax.taxrate.TaxRate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service("taxServiceVtx")
public class TaxServiceImplVtx
		implements TaxService {
	
	private final static String TAX_CONFIGURATION = "TAX_CONFIG";
	private final static String DEFAULT_TAX_CLASS = "DEFAULT";
	
	@Inject
	private MerchantConfigurationService merchantConfigurationService;
	
	@Inject
	private TaxRateService taxRateService;
	
	@Inject
	private TaxClassService taxClassService;
	
	@Override
	public TaxConfiguration getTaxConfiguration(MerchantStore store) throws ServiceException {
		
		
		
		MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(TAX_CONFIGURATION, store);
		TaxConfiguration taxConfiguration = null;
		if(configuration!=null) {
			String value = configuration.getValue();
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				taxConfiguration = mapper.readValue(value, TaxConfiguration.class);
			} catch(Exception e) {
				throw new ServiceException("Cannot parse json string " + value);
			}
		}
		return taxConfiguration;
	}
	
	
	@Override
	public void saveTaxConfiguration(TaxConfiguration shippingConfiguration, MerchantStore store) throws ServiceException {
		
		MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(TAX_CONFIGURATION, store);

		if(configuration==null) {
			configuration = new MerchantConfiguration();
			configuration.setMerchantStore(store);
			configuration.setKey(TAX_CONFIGURATION);
		}
		
		String value = shippingConfiguration.toJSONString();
		configuration.setValue(value);
		merchantConfigurationService.saveOrUpdate(configuration);
		
	}


	@Override
	public List<TaxItem> calculateTax(OrderSummary orderSummary, Customer customer, MerchantStore store, Language language) throws ServiceException {

		List<ShoppingCartItem> itemsVtx = orderSummary.getProducts();
		List<TaxItem> list = new ArrayList<TaxItem>();
		VtxTaxService vtx=new VtxTaxService();
		VtxTaxCalc vtxEngineCalculation=new VtxTaxCalc();

		String 	accessToken = null;
		try {
			accessToken = vtx.getAuthenticate();
		//	 vtxEngineCalculation=vtx.doCalculation(accessToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}



		if(customer==null) {
			return null;
		}

		List<ShoppingCartItem> items = orderSummary.getProducts();

		List<TaxItem> taxLines = new ArrayList<TaxItem>();

		if(items==null) {
			return taxLines;
		}


		Country country = customer.getBilling().getCountry();
		Zone zone = customer.getBilling().getZone();
		String stateProvince = customer.getBilling().getState();


		if(zone == null && StringUtils.isBlank(stateProvince)) {
			return null;
		}

		List<TaxItem> taxItems = new ArrayList<TaxItem>();

		//put items in a map by tax class id

		for(ShoppingCartItem item : items) {

			BigDecimal itemPrice = item.getItemPrice();

			int quantity = item.getQuantity();
			itemPrice = itemPrice.multiply(new BigDecimal(quantity));

			BigDecimal subTotal = new BigDecimal(0);
			subTotal.setScale(2, RoundingMode.HALF_UP);


			subTotal = subTotal.add(itemPrice);
			TaxItem t =new TaxItem();
			TaxRate r=new TaxRate();
			r.setTaxRate(new BigDecimal(9));

			t.setTaxRate(r);
			t.setLabel("sss");






		}










		return list;

	}
}
