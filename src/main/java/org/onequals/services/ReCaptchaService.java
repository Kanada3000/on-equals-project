package org.onequals.services;

import org.onequals.recaptcha.ReCaptchaResponse;

public interface ReCaptchaService {

    ReCaptchaResponse verify(String response);
}
