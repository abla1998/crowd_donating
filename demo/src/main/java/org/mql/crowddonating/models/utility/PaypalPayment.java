package org.mql.crowddonating.models.utility;

import org.mql.crowddonating.config.PaypalPaymentIntent;
import org.mql.crowddonating.config.PaypalPaymentMethod;

public class PaypalPayment {
    private Double total;
    private String currency;
    private PaypalPaymentMethod method;
    private PaypalPaymentIntent intent;
    private String description;
    private String cancelUrl;
    private String successUrl;
    private String custom;

    public PaypalPayment() {
    }

    public PaypalPayment(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl, String custom) {
        this.total = total;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.cancelUrl = cancelUrl;
        this.successUrl = successUrl;
        this.custom = custom;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PaypalPaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaypalPaymentMethod method) {
        this.method = method;
    }

    public PaypalPaymentIntent getIntent() {
        return intent;
    }

    public void setIntent(PaypalPaymentIntent intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }
}
