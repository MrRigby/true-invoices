package com.github.mrrigby.trueinvoices.infrastructure;

import com.github.mrrigby.trueinvoices.infrastructure.config.RepositoryConfig;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceItemEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoicePurchaserEntity;
import com.github.mrrigby.trueinvoices.infrastructure.entity.PurchaserDataEmbeddable;
import com.github.mrrigby.trueinvoices.model.PaymentKind;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

    }

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfig.class);
        LocalSessionFactoryBean localSessionFactoryBean = ctx.getBean(LocalSessionFactoryBean.class);
        SessionFactory sessionFactory = localSessionFactoryBean.getObject();

        // valueOf session
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        initDictionaries(session);

        InvoiceItemEntity pos1 = new InvoiceItemEntity();
        pos1.setQuantity(1);
        pos1.setCommodity("Wycinka drzew");
        pos1.setAuxiliarySymbol("SWW/KWiU/124.554");
        pos1.setMeasure("Sztuka");
        pos1.setSingleNetPrice(new BigDecimal(1500.99));
        pos1.setTaxRate((short) 7);

        InvoiceItemEntity pos2 = new InvoiceItemEntity();
        pos2.setQuantity(1);
        pos2.setCommodity("Sadzenie trawy");
        pos2.setAuxiliarySymbol("SWW/KWiU/678.321");
        pos2.setMeasure("Sztuka");
        pos2.setSingleNetPrice(new BigDecimal(300.49));
        pos2.setTaxRate((short) 3);

        PurchaserDataEmbeddable purchaserData = new PurchaserDataEmbeddable();
        purchaserData.setName("Piekarz");
        purchaserData.setAddress("ul. Piekaniana 7, 60-456 Poznan");
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
