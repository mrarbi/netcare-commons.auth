/*
 * Inera Webcert - Sjukintygsapplikation
 *
 * Copyright (C) 2010-2011 Inera AB (http://www.inera.se)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.callistasoftware.netcare.commons.auth.spi.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3.wsaddressing10.AttributedURIType;
import org.callistasoftware.netcare.commons.auth.hsaws.v3.HsaWsResponderInterface;
import org.callistasoftware.netcare.commons.auth.hsaws.v3.HsaWsResponderService;
import org.callistasoftware.netcare.commons.auth.hsawsresponder.v3.*;

import javax.xml.ws.BindingProvider;
import java.net.URL;

@Service
public class HSAWebServiceCalls implements InitializingBean {
    
    private static final Logger log = LoggerFactory.getLogger(HSAWebServiceCalls.class);
    
    private AttributedURIType logicalAddressHeader = new AttributedURIType();;
    
    private AttributedURIType messageId = new AttributedURIType();

    private HsaWsResponderInterface serverInterface;

    @Value("${hsa.ws.services.url}")
    private String hsaUrl;
    
    @Value("${hsa.ws.service.logicaladdress}")
    private String hsaLogicalAddress;

    /**
     * @param hsaUrl the hsaUrl to set
     */
    public void setHsaUrl(String hsaUrl) {
        this.hsaUrl = hsaUrl;
    }

    /**
     * @param hsaLogicalAddress the hsaLogicalAddress to set
     */
    public void setHsaLogicalAddress(String hsaLogicalAddress) {
        this.hsaLogicalAddress = hsaLogicalAddress;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
    	log.debug("expecting these properties to be already set {}, {}", hsaUrl, hsaLogicalAddress);
        logicalAddressHeader.setValue(hsaLogicalAddress);
        
        serverInterface = getServerInterface(hsaUrl);
    }
    
    /**
     * Help method to test access to HSA
     * @throws Exception 
     */
    public void callPing() throws Exception {

        try {
            PingType pingtype = new PingType();
            PingResponseType response = serverInterface.ping(logicalAddressHeader, messageId, pingtype);
            log.debug("Response:" + response.getMessage());

        } catch (Exception ex) {
            log.warn("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }

    /**
     * Method used to get miuRights for a HoS Person
     * @param parameters
     * @return
     * @throws Exception 
     */
    public GetMiuForPersonResponseType callMiuRights(LookupHsaObjectType parameters) throws Exception {
//        try {
//            GetMiuForPersonResponseType response = serverInterface.getMiuForPerson(logicalAddressHeader, messageId,
//                    parameters);
//            return response;
//        } catch (Exception ex) {
//            log.error("Exception={}", ex.getMessage(), ex);
//            throw new Exception(ex);
//        }
        throw new Exception("Code not implemented here yet");

//    	return null;
    }

    /**
     * Method to retrieve data for a hsa unit
     * @param hsaId
     * @throws Exception 
     */
    public GetCareUnitResponseType callGetCareunit(String hsaId) throws Exception {
        LookupHsaObjectType parameters = new LookupHsaObjectType();
        parameters.setHsaIdentity(hsaId);

        try {
            GetCareUnitResponseType response = serverInterface.getCareUnit(logicalAddressHeader, messageId, parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }

    /**
     * Method to retrieve the caregiver for a hsa unit
     * @param hsaId
     * @throws Exception 
     */
    public GetHsaUnitResponseType callGetHsaunit(String hsaId) throws Exception {
        LookupHsaObjectType parameters = new LookupHsaObjectType();
        parameters.setHsaIdentity(hsaId);

        try {
            GetHsaUnitResponseType response = serverInterface.getHsaUnit(logicalAddressHeader, messageId, parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
        }
    }
    
    /**
     * Method to retrieve attributes for a HoS Person
     * @param parameters
     * @return
     * @throws Exception 
     */
    public HsawsSimpleLookupResponseType callHsawsSimpleLookup(HsawsSimpleLookupType parameters) throws Exception {
        try {
            HsawsSimpleLookupResponseType response = serverInterface.hsawsSimpleLookup(logicalAddressHeader, messageId,
                    parameters);
            return response;
        } catch (Exception ex) {
            log.error("Exception={}", ex.getMessage(), ex);
            throw new Exception(ex);
       }
    }

   
    private HsaWsResponderInterface getServerInterface(String wsUrl) {
        try {
        	log.debug("wsUrl is {}", wsUrl);
            // Get URL to wsdl file
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            //TODO: Get this path out of code. Was problematic when changing from 3.8 to 3.14 and will be for future changes if it stays here
            URL wsdlUrl = loader.getResource("schemas/wsdl/HsaWsInteraction_3.14.wsdl");

            // Create web service client stub		
            HsaWsResponderService hwrs = new HsaWsResponderService(wsdlUrl); 
            HsaWsResponderInterface hwri = hwrs.getHsaWsResponderPort();
            
            // Set web service server url
            BindingProvider provider = (BindingProvider) hwri;
            provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
            return hwri;
        } catch (Throwable ex) {
        	log.error(ex.toString());
            log.error("Exception={}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
}
