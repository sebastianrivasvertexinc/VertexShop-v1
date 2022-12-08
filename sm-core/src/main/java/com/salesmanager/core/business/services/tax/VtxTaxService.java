package com.salesmanager.core.business.services.tax;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.salesmanager.core.business.services.tax.vertex.VtxTaxCalc;
import com.salesmanager.core.business.services.tax.vertex.VtxTaxCalcReq;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class VtxTaxService {
private String authURL;
private String restURL;
private String clientId;
private String clientSecret;
    private final OkHttpClient httpClient = new OkHttpClient();

    public static <OkHttpExample4> void main(String[] args) throws IOException {
        String accessToken =getAuthenticate();
        VtxTaxCalc vtx=doCalculation(null, accessToken);
    //    System.out.println("Total tax "+vtx.data.lineItems.get(0).totalTax);





    }

    public static VtxTaxCalc doCalculation(VtxTaxCalcReq calcRequest, String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Gson gson =new Gson();
        MediaType mediaType = MediaType.parse("application/json");
     //   RequestBody body = RequestBody.create(mediaType, "{\n    \"saleMessageType\": \"QUOTATION\",\n    \"seller\": {\n        \"company\": \"COMPANY\"\n    },\n    \"customer\": {\n        \"customerCode\": {\n            \"classCode\": \"custclass\",\n            \"value\": \"cust\"\n        },\n        \"destination\": {\n            \"streetAddress1\": \"90 Biesterfield Rd\",\n            \"city\": \"Elk Grove Village\",\n            \"country\": \"USA\",\n            \"mainDivision\": \"IL\",\n            \"postalCode\": \"60007\"\n        }\n    },\n    \"lineItems\": [\n        {\n           \n            \"product\": {\n                \"productClass\": \"CLOTHING\"\n            },\n            \"quantity\": {\n                \"value\": 10\n            },\n            \"unitPrice\": 10,\n            \"flexibleFields\": {\n                \"flexibleCodeFields\": [\n                    {\n                        \"fieldId\": 1,\n                        \"value\": \"FLEXCodeField1\"\n                    }\n                ],\n                \"flexibleNumericFields\": [\n                    {\n                        \"fieldId\": 1,\n                        \"value\": 111\n                    }\n                ],\n                \"flexibleDateFields\": [\n                    {\n                        \"fieldId\": 1,\n                        \"value\": \"2017-12-18\"\n                    }\n                ]\n            },\n            \"lineItemNumber\": 1,\n            \"deliveryTerm\": \"FOB\"\n        },\n        {\n            \"seller\": {\n                \"physicalOrigin\": {\n                    \"taxAreaId\": 391013000\n                }\n            },\n            \"product\": {\n                \"productClass\": \"SHIPPING\",\n                \"value\": \"SHIPPING\"\n            },\n            \"quantity\": {\n                \"value\": 1\n            },\n            \"extendedPrice\": 20,\n            \"lineItemNumber\": 2\n        }\n    ],\n    \"documentNumber\": \"Quote-12345\",\n    \"documentDate\": \"2021-12-18\",\n    \"transactionType\": \"SALE\"\n}");
        RequestBody body = RequestBody.create(mediaType, gson.toJson(calcRequest,VtxTaxCalcReq.class));

        Request request = new Request.Builder()
                .url("https://sales-oseries9.ondemand.vertexinc.com/vertex-ws/v2/supplies")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+accessToken.toString())
                //  .addHeader("Cookie", "JSESSIONID=29CB597EED78A1E472F700751BD4E3EE; BIGipServerpool_sales09_OnDemand=543038986.20480.0000")
                .build();
        Response response = client.newCall(request).execute();


        VtxTaxCalc vtx=new VtxTaxCalc();
        vtx=gson.fromJson(response.body().string(),VtxTaxCalc.class);
        return vtx;
    }

    public static String getAuthenticate() throws IOException {
        OkHttpClient auth = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaTypeAuth = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody bodyAuth = RequestBody.create(mediaTypeAuth, "client_id=fd2b85af77da.vertexinc.com&client_secret=3c927e1e67329ef4efeb3a7f91004231328f7f194afdf7cec176f9bcea767b6d&grant_type=client_credentials");
        Request requestAuth = new Request.Builder()
                .url("https://sales-oseries9.ondemand.vertexinc.com/oseries-auth/oauth/token")
                .method("POST", bodyAuth)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                //   .addHeader("Cookie", "BIGipServerpool_sales09_OnDemand=543038986.20480.0000")
                .build();
        Response responseAuth = auth.newCall(requestAuth).execute();
        Map<String, Object> responseMap = new ObjectMapper().readValue(responseAuth.body().byteStream(), HashMap.class);
        // Read the value of the "access_token" key from the hashmap
        String accessToken = (String) responseMap.get("access_token");
        return accessToken;


    }


}