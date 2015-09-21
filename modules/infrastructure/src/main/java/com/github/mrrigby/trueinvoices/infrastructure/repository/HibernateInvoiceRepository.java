package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.mapper.InvoiceMapper;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author MrRigby
 */
@Repository
public class HibernateInvoiceRepository implements InvoiceRepository {

    private SessionFactory sessionFactory;
    private InvoiceMapper invoiceMapper;

    @Autowired
    public HibernateInvoiceRepository(SessionFactory sessionFactory, InvoiceMapper invoiceMapper) {
        this.sessionFactory = sessionFactory;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public Invoice getById(Long id) {
        InvoiceEntity invoiceEntity = sessionFactory.getCurrentSession().get(InvoiceEntity.class, id);
        return invoiceMapper.entityToModel(invoiceEntity);
    }

    @Override
    public Invoice getByBusinessId(String businessId) {

        Criteria invoiceEntityCriteria = sessionFactory.getCurrentSession().createCriteria(InvoiceEntity.class)
                .add(Restrictions.eq("businessId", businessId));

        InvoiceEntity invoiceEntity = (InvoiceEntity) invoiceEntityCriteria.uniqueResult();
        return invoiceMapper.entityToModel(invoiceEntity);
    }

    @Override
    public void save(Invoice invoice) {

    }

    @Override
    public void update(Invoice invoice) {

    }
}
