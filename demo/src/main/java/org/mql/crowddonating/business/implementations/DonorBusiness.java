package org.mql.crowddonating.business.implementations;

import javax.transaction.Transactional;

import org.mql.crowddonating.business.IDonorBusiness;
import org.mql.crowddonating.dao.ConfirmationTokenRepository;
import org.mql.crowddonating.dao.DonationRepository;
import org.mql.crowddonating.dao.DonorRepository;
import org.mql.crowddonating.dao.RoleRepository;
import org.mql.crowddonating.models.ConfirmationToken;
import org.mql.crowddonating.models.Donation;
import org.mql.crowddonating.models.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DonorBusiness extends UserBusiness implements IDonorBusiness {
    @Autowired
    private DonationRepository donationDao;

    @Autowired
    private DonorRepository donorDao;

    @Autowired
    private ConfirmationToken confirmationToken;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenDao;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleDao;

    @Override
    public Donation addDonation(Donation donation) {
        return donationDao.save(donation);
    }

    @Override
    public void signup(Donor donor) {
        donor.addRole(roleDao.findByRole("DONATOR"));
        donor.setAvatar("cover.jpg");
        donor.setPassword(bCryptPasswordEncoder.encode(donor.getPassword()));
        donorDao.save(donor);
        mailConfirmation(donor);
    }

    @Override
    public void mailConfirmation(Donor donor) {
        confirmationToken.setUser(donor);
        confirmationTokenDao.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(donor.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("mql.donating@gmail.com");
        mailMessage.setText("To confirm your account, please click here : " + "http://localhost:8080/confirm-account/"
                + confirmationToken.getConfirmationToken());

        javaMailSender.send(mailMessage);
    }

    @Override
    public Boolean confirmation(String confirmationToken) {
        ConfirmationToken token = confirmationTokenDao.findByConfirmationToken(confirmationToken);
        boolean statusPage = false;
        if (token != null) {
            Donor donor = donorDao.findByEmailIgnoreCase(token.getUser().getEmail());
            donor.setEnabled(true);
            donorDao.save(donor);
            statusPage = true;
        }
        return statusPage;
    }

    @Override
    public Donor getByUsername(String username) {
        return donorDao.getByUsername(username);
    }
}