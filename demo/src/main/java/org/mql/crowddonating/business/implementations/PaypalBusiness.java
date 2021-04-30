package org.mql.crowddonating.business.implementations;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.json.JSONObject;
import org.mql.crowddonating.business.IPaypalBusiness;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.utility.PaypalPayment;
import org.mql.crowddonating.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaypalBusiness implements IPaypalBusiness {
    @Autowired
    private APIContext apiContext;

    @Autowired
    private DonorBusiness donorBusiness;

    @Autowired
    private PublicServicesBusiness publicServices;

    @Override
    public Payment createPayment(PaypalPayment paypalPayment) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(paypalPayment.getCurrency());
        paypalPayment.setTotal(new BigDecimal(paypalPayment.getTotal()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        amount.setTotal(String.format("%.2f", paypalPayment.getTotal()));

        Transaction transaction = new Transaction();
        transaction.setDescription(paypalPayment.getDescription());
        transaction.setAmount(amount);
        // This is a custom attribute, can be used for sending data back to the server.
        transaction.setCustom(paypalPayment.getCustom());

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(paypalPayment.getMethod().toString());
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setEmail("imouad.you-buyer@gmail.com");
        payerInfo.setPayerId("1");
        payerInfo.setFirstName("Mouad");
        payerInfo.setLastName("Youssef");
        payer.setPayerInfo(payerInfo);

        Payee payee = new Payee();
//        payee.setAccountNumber("7333646453");
        payee.setEmail("imouad.you-facilitator@gmail.com");

        Payment payment = new Payment();
        payment.setIntent(paypalPayment.getIntent().toString());
        payment.setPayer(payer);
        payment.setPayee(null);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(paypalPayment.getCancelUrl());
        redirectUrls.setReturnUrl(paypalPayment.getSuccessUrl());
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    @Override
    public String successPayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = executePayment(paymentId, payerId);

        if (payment.getState().equals("approved")) {
//            String currencyConverterApiJson = Utility.getJsonFromUrl("http://free.currencyconverterapi.com/api/v6/convert?q=MAD_USD&compact=ultra");
//            JSONObject obj = new JSONObject(currencyConverterApiJson);
//            double dollarPrice = obj.getDouble("MAD_USD");

            double dollarPrice = 9.54;
            JSONObject json = new JSONObject(payment.toJSON());
            String payment_id = json.getString("id");
            String custom = json.getJSONArray("transactions")
                    .getJSONObject(0)
                    .getString("custom");
            json = json.getJSONArray("transactions")
                    .getJSONObject(0)
                    .getJSONArray("related_resources")
                    .getJSONObject(0)
                    .getJSONObject("sale");
            String transaction_id = json.getString("id");
            double transaction_fee = json.getJSONObject("transaction_fee").getDouble("value");
            double amount = json.getJSONObject("amount").getDouble("total");

            Long case_id = null, project_id = null;
            try {
                case_id = new JSONObject(custom).getLong("case_id");
            } catch (Exception ignored) {
            }
            try {
                project_id = new JSONObject(custom).getLong("project_id");
            } catch (Exception ignored) {
            }
//            case_id = new JSONObject(custom).getLong("case_id");
//            project_id = new JSONObject(custom).getLong("project_id");
            double mad_amount = new JSONObject(custom).getDouble("mad_amount");

            transaction_fee = Utility.round((transaction_fee * dollarPrice), 2);
            //amount = Utility.round((amount * dollarPrice), 2);

            Donation donation = new Donation();
            donation.setAmount(mad_amount);
            donation.setPaypalId(payment_id);
            donation.setTransactionId(transaction_id);
            donation.setTransactionFee(transaction_fee);
            donation.setDate(new Date());

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            donation.setDonor(donorBusiness.getByUsername(auth.getName()));
            donation.setCase(case_id == null ? null : publicServices.getCaseById(case_id));
            donation.setProject(project_id == null ? null : publicServices.getProjectById(project_id));

            donorBusiness.addDonation(donation);

            if (case_id != null)
                return "/cases/" + publicServices.getCaseById(case_id).getSlug();
            else if (project_id != null)
                return "/projects/" + publicServices.getProjectById(project_id).getSlug();
        }
        return "/error/500";
    }
}
