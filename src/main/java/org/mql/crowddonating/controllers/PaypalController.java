package org.mql.crowddonating.controllers;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.json.JSONObject;
import org.mql.crowddonating.business.IPaypalBusiness;
import org.mql.crowddonating.config.PaypalPaymentIntent;
import org.mql.crowddonating.config.PaypalPaymentMethod;
import org.mql.crowddonating.models.utility.PaypalPayment;
import org.mql.crowddonating.models.utility.PaypalPaymentBuilder;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PaypalController {

    private static final String PAYPAL_SUCCESS_URL = "/paypal/pay/success";
    private static final String PAYPAL_CANCEL_URL = "/paypal/pay/cancel";

    @Autowired
    IPaypalBusiness paypalBusiness;

    @GetMapping("/paypal")
    public String home() {
        return "paypal/index";
    }

    @PostMapping("/cases/paypal")
    public String pay(HttpServletRequest request, @RequestParam long id, @RequestParam double value) {
        String cancelUrl = Utility.getBaseURL(request) + PAYPAL_CANCEL_URL;
        String successUrl = Utility.getBaseURL(request) + PAYPAL_SUCCESS_URL;

        try {
            //TODO: fix this
//            String json = Utility.getJsonFromUrl("http://free.currencyconverterapi.com/api/v6/convert?q=MAD_USD&compact=ultra");

//            JSONObject obj = new JSONObject(json);
//            double dollarPrice = obj.getDouble("MAD_USD");

            double dollarPrice = 0.103851;

            JSONObject custom = new JSONObject();
            custom.put("case_id", id);
            custom.put("mad_amount", value);

            PaypalPayment paypalPayment = new PaypalPaymentBuilder()
                    .setTotal(value * dollarPrice)
                    .setCurrency("USD")
                    .setMethod(PaypalPaymentMethod.paypal)
                    .setIntent(PaypalPaymentIntent.sale)
                    .setCancelUrl(cancelUrl)
                    .setSuccessUrl(successUrl)
                    // TODO: update this
                    .setDescription("Crowd Donating payment description")
                    .setCustom(custom.toString())
                    .build();
            Payment payment = paypalBusiness.createPayment(paypalPayment);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/paypal";
    }

    @GetMapping(value = PAYPAL_SUCCESS_URL, produces = "application/json")
    public String success(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        // TODO: implement refund functionality
        try {
            // returns boolean
            String return_url = paypalBusiness.successPayment(paymentId, payerId);
            return "redirect:" + return_url + "?success=true";
        } catch (PayPalRESTException e) {
            System.err.println(e.getMessage());
        }
        return "/";
    }

    @ResponseBody
    @GetMapping(PAYPAL_CANCEL_URL)
    public String cancel() {
        return "Canceled.";
    }

    @PostMapping("/projects/paypal")
    public String payProject(HttpServletRequest request, @RequestParam long id, @RequestParam double value) {
        String cancelUrl = Utility.getBaseURL(request) + PAYPAL_CANCEL_URL;
        String successUrl = Utility.getBaseURL(request) + PAYPAL_SUCCESS_URL;
        try {
            double dollarPrice = 0.103851;

            JSONObject custom = new JSONObject();
            custom.put("project_id", id);
            custom.put("mad_amount", value);

            PaypalPayment paypalPayment = new PaypalPaymentBuilder()
                    .setTotal(value * dollarPrice)
                    .setCurrency("USD")
                    .setMethod(PaypalPaymentMethod.paypal)
                    .setIntent(PaypalPaymentIntent.sale)
                    .setCancelUrl(cancelUrl)
                    .setSuccessUrl(successUrl)
                    // TODO: update this
                    .setDescription("Crowd Donating - Donating for a project")
                    .setCustom(custom.toString())
                    .build();
            Payment payment = paypalBusiness.createPayment(paypalPayment);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getMessage());
        }
        return "redirect:/paypal";
    }

}
