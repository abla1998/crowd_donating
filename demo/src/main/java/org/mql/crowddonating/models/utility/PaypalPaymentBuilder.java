package org.mql.crowddonating.models.utility;

import org.mql.crowddonating.config.PaypalPaymentIntent;
import org.mql.crowddonating.config.PaypalPaymentMethod;

public class PaypalPaymentBuilder {
    private Double total;
    private String currency;
    private PaypalPaymentMethod method = PaypalPaymentMethod.paypal;
    private PaypalPaymentIntent intent = PaypalPaymentIntent.sale;
    private String cancelUrl;
    private String successUrl;
    private String description = "";
    private String custom;

    public PaypalPaymentBuilder setTotal(Double total) {
        this.total = total;
        return this;
    }

    public PaypalPaymentBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public PaypalPaymentBuilder setMethod(PaypalPaymentMethod method) {
        this.method = method;
        return this;
    }

    public PaypalPaymentBuilder setIntent(PaypalPaymentIntent intent) {
        this.intent = intent;
        return this;
    }

    public PaypalPaymentBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public PaypalPaymentBuilder setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
        return this;
    }

    public PaypalPaymentBuilder setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
        return this;
    }

    public PaypalPaymentBuilder setCustom(String custom) {
        this.custom = custom;
        return this;
    }

    public PaypalPayment build() {
        return new PaypalPayment(total, currency, method, intent, description, cancelUrl, successUrl, custom);
    }
}