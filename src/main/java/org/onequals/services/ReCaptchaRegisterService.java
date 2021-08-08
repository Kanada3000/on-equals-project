package org.onequals.services;

import org.onequals.recaptcha.ReCaptchaKeys;
import org.onequals.recaptcha.ReCaptchaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ReCaptchaRegisterService implements ReCaptchaService {

    private final ReCaptchaKeys reCaptchaKeys;
    private final RestTemplate restTemplate;

    @Autowired
    public ReCaptchaRegisterService(ReCaptchaKeys reCaptchaKeys, RestTemplate restTemplate) {
        this.reCaptchaKeys = reCaptchaKeys;
        this.restTemplate = restTemplate;
    }

    @Override
    public ReCaptchaResponse verify(String response) {

        URI verifyURI = URI.create(
                String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",
                        reCaptchaKeys.getSecret(), response));

        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(verifyURI, ReCaptchaResponse.class);

        if (reCaptchaResponse != null)
            if (reCaptchaResponse.isSuccess() && reCaptchaResponse.getScore() < reCaptchaKeys.getThreshold())
                reCaptchaResponse.setSuccess(false);
        return reCaptchaResponse;
    }
}
