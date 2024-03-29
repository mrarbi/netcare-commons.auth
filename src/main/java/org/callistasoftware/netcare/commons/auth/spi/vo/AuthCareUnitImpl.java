/**
 *    Copyright 2011,2012 Callista Enterprise AB
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.callistasoftware.netcare.commons.auth.spi.vo;

import java.io.Serializable;

import org.callistasoftware.netcare.commons.auth.api.CareGiverInterface;
import org.callistasoftware.netcare.commons.auth.api.CareUnitInterface;


public class AuthCareUnitImpl implements CareUnitInterface, Serializable {
    private static final long serialVersionUID = 1L;

    private String hsaId;
    private String name;
    private String workplaceCode;
    private String email;
    private String phone;
    private String postalAddress;
    private String postalCode;
    private String postalCity;
    
    private CareGiverInterface careGiver;

    public void setHsaId(String hsaId) {
        this.hsaId = hsaId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkplaceCode(String workplaceCode) {
        this.workplaceCode = workplaceCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCareGiver(CareGiverInterface careGiver) {
        this.careGiver = careGiver;
    }

    public String getHsaId() {
        return hsaId;
    }

    public String getName() {
        return name;
    }

    public String getWorkplaceCode() {
        return workplaceCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPostalCity() {
        return postalCity;
    }

    @Override
    public String toString() {
        return "AuthCareUnitImpl [hsaId=" + hsaId + ", name=" + name + ", workplaceCode=" + workplaceCode + ", email="
                + email + ", phone=" + phone + ", careGiver=" + careGiver + "]";
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPostalCity(String postalCity) {
        this.postalCity = postalCity;
    }

	public String getId() {
		return this.getHsaId();
	}

	@Override
	public CareGiverInterface getCareGiver() {
		return careGiver;
	}

}
