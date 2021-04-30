package org.mql.crowddonating.business;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.mql.crowddonating.models.utility.PaypalPayment;

public interface IPaypalBusiness {
    Payment createPayment(PaypalPayment paypalPayment) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

    String successPayment(String paymentId, String payerId) throws PayPalRESTException;
}
