/**
 * @author Dipak
 */

package com.pscs.moneyxhub.feignclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pscs.moneyxhub.model.LicenseRequest;

@FeignClient(name = "pscs-license",url = "${license.service.url}")
public interface LicenseFeignClient {
	@PostMapping("/generate")
	public Map<String, String> generateLicense(@RequestBody LicenseRequest req);

	@PostMapping("/validate")
	public Map<String, Boolean> validateLicense(@RequestBody Map<String, String> body);
	
	@PostMapping("/revoke/{id}")
    public Map<String, String> revokeLicense(@PathVariable("id") Long id);
}
