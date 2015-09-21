package com.github.mrrigby.trueinvoices.infrastructure;

import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig;
import com.github.mrrigby.trueinvoices.infrastructure.entity.*;
import com.github.mrrigby.trueinvoices.model.PaymentKind;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author MrRigby
 */
public class TestApp {

    private static void initDictionaries(Session session) {

        TaxRateEntity taxRate0 = new TaxRateEntity();
        taxRate0.setDescription("Rate 0%");
        taxRate0.setValue((short) 0);
        session.persist(taxRate0);

        TaxRateEntity taxRate3 = new TaxRateEntity();
        taxRate3.setDescription("Rate 3%");
        taxRate3.setValue((short) 3);
        session.persist(taxRate3);

        TaxRateEntity taxRate7 = new TaxRateEntity();
        taxRate7.setDescription("Rate 7%");
        taxRate7.setValue((short) 7);
        session.persist(taxRate7);

        TaxRateEntity taxRate23 = new TaxRateEntity();
        taxRate23.setDescription("Rate 23%");
        taxRate23.setValue((short) 23);
        session.persist(taxRate23);
    }

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
        LocalSessionFactoryBean localSessionFactoryBean = ctx.getBean(LocalSessionFactoryBean.class);
        SessionFactory sessionFactory = localSessionFactoryBean.getObject();

        // create session
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        initDictionaries(session);

        TaxRateEntity taxRate3 = (TaxRateEntity) session.createCriteria(TaxRateEntity.class)
                .add(Restrictions.eq("value", (short) 3)).uniqueResult();
        TaxRateEntity taxRate7 = (TaxRateEntity) session.createCriteria(TaxRateEntity.class)
                .add(Restrictions.eq("value", (short) 7)).uniqueResult();

        InvoiceItemEntity pos1 = new InvoiceItemEntity();
        pos1.setQuantity(1);
        pos1.setCommodity("Wycinka drzew");
        pos1.setSymbol("SWW/KWiU/124.554");
        pos1.setMeasure("Sztuka");
        pos1.setSingleNetPrice(new BigDecimal(1500.99));
        pos1.setTaxRate(taxRate7);

        InvoiceItemEntity pos2 = new InvoiceItemEntity();
        pos2.setQuantity(1);
        pos2.setCommodity("Sadzenie trawy");
        pos2.setSymbol("SWW/KWiU/678.321");
        pos2.setMeasure("Sztuka");
        pos2.setSingleNetPrice(new BigDecimal(300.49));
        pos2.setTaxRate(taxRate3);

        PurchaserDataEmbeddable purchaserData = new PurchaserDataEmbeddable();
        purchaserData.setName("Piekarz");
        purchaserData.setAddress("ul. Piekaniana 7, 60-456 Poznañ");
        purchaserData.setTaxId("1234554321");
        InvoicePurchaserEntity purchaser = new InvoicePurchaserEntity();
        purchaser.setPurchaserData(purchaserData);
        purchaser.setRole("Nabywca");

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setBusinessId("2015/09/03");
        invoice.setDocumentDate(new Date(2015 - 1900, 9 - 1, 16));
        invoice.setSoldDate(new Date(2015 - 1900, 9 - 1, 16));
        invoice.setPaymentDate(new Date(2015 - 1900, 9 - 1, 30));
        invoice.setPaymentKind(PaymentKind.CASH);
        invoice.setItems(Arrays.asList(pos1, pos2));
        invoice.setPurchasers(Arrays.asList(purchaser));

        session.persist(invoice);

        session.getTransaction().commit();
        session.close();
        // end of session
    }
}
