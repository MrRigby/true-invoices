package com.github.mrrigby.trueinvoices.infrastructure.repository;

import com.github.mrrigby.trueinvoices.infrastructure.entity.InvoiceEntity;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.HibernateRepository;
import com.github.mrrigby.trueinvoices.infrastructure.repository.mapper.InvoiceMapper;
import com.github.mrrigby.trueinvoices.model.Invoice;
import com.github.mrrigby.trueinvoices.repository.InvoiceRepository;
import com.github.mrrigby.trueinvoices.repository.dto.InvoiceListFilter;
import com.github.mrrigby.trueinvoices.repository.exceptions.InvoiceNotFoundException;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hibernate.criterion.Restrictions.like;

/**
 * Hibernate-based implementation of the {@Link InvoiceRepository}, implemented as an extension of
 * {@link HibernateRepository}.
 *
 * @author MrRigby
 */
@Repository
public class HibernateInvoiceRepository extends HibernateRepository
        implements InvoiceRepository {

    private InvoiceMapper invoiceMapper;

    @Autowired
    public HibernateInvoiceRepository(SessionFactory sessionFactory, InvoiceMapper invoiceMapper) {
        super(sessionFactory);
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getById(Long id) throws InvoiceNotFoundException {

        InvoiceEntity invoiceEntity = (InvoiceEntity) session().get(InvoiceEntity.class, id);
        if (invoiceEntity == null) {
            throw new InvoiceNotFoundException(
                    String.format("No invoice with id=[%d]", id));
        }

        return invoiceMapper.entityToModel(invoiceEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getByBusinessId(String businessId) throws InvoiceNotFoundException {

        Criteria invoiceEntityCriteria = session().createCriteria(InvoiceEntity.class)
                .add(Restrictions.eq("businessId", businessId));

        InvoiceEntity invoiceEntity = (InvoiceEntity) invoiceEntityCriteria.uniqueResult();
        if (invoiceEntity == null) {
            throw new InvoiceNotFoundException(
                    String.format("No invoice with businessId=[%s]", businessId));
        }

        return invoiceMapper.entityToModel(invoiceEntity);
    }

    @Override
    @Transactional
    public Page<Invoice> listPage(Pageable pageable, InvoiceListFilter filter) {

        Criteria pageableCriteria = criteriaForListFilter(filter)
                .setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .addOrder(Order.desc("documentDate").desc("id"));

        List<InvoiceEntity> entities = pageableCriteria.list();
        List<Invoice> invoices = entities.stream()
                .map(invoiceMapper::entityToModel)
                .collect(toList());

        Long invoicesCount = count(filter);

        return new PageImpl<>(invoices, pageable, invoicesCount);
    }

    private Long count(InvoiceListFilter filter) {
        return (Long) criteriaForListFilter(filter)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    private Criteria criteriaForListFilter(InvoiceListFilter filter) {

        Criteria criteria = session()
                .createCriteria(InvoiceEntity.class);
        if (filter == null) {
            return criteria;
        }

        if (!Strings.isNullOrEmpty(filter.getBusinessIdMask())) {
            criteria.add(like("businessId", String.format("%%%s%%", filter.getBusinessIdMask())));
        }
        if (filter.getDateFrom() != null) {
            Date dateFrom = Date.from(filter.getDateFrom().atStartOfDay(ZoneId.systemDefault()).toInstant());
            criteria.add(Restrictions.ge("documentDate", dateFrom));
        }
        if (filter.getDateTo() != null) {
            Date dateTo = Date.from(filter.getDateTo().atStartOfDay(ZoneId.systemDefault()).toInstant());
            criteria.add(Restrictions.le("documentDate", dateTo));
        }
        if (!Strings.isNullOrEmpty(filter.getPurchaserMask())) {
            criteria.add(like("purchasers.name", String.format("%%%s%%", filter.getPurchaserMask())));
        }

        return criteria;
    }

    @Override
    @Transactional
    public Invoice save(Invoice invoice) {
        Preconditions.checkNotNull(invoice);
        Preconditions.checkArgument(!invoice.getId().isPresent());

        InvoiceEntity detachedEntity = invoiceMapper.modelToEntity(invoice);
        Long invoiceId = (Long) session().save(detachedEntity);

        return getById(invoiceId);
    }

    /**
     * Updates the invoice. The invoice has to exist in the datastore, otherwise InvoiceNotFoundException will be thrown.
     *
     * @param invoice invoice object to update
     * @throws InvoiceNotFoundException when no invoice with given {@link Invoice#getId()} found to update
     */
    @Override
    @Transactional
    public void update(Invoice invoice) throws InvoiceNotFoundException {
        Preconditions.checkNotNull(invoice);
        Preconditions.checkArgument(invoice.getId().isPresent());
        Long invoiceId = invoice.getId().get();

        InvoiceEntity actualInvoiceEntity = (InvoiceEntity) session().get(InvoiceEntity.class, invoiceId);
        if (actualInvoiceEntity == null) {
            throw new InvoiceNotFoundException(
                    String.format("No invoice to update found for id=[%d]", invoiceId));
        }

        InvoiceEntity invoiceEntityToUpdate = invoiceMapper.modelToEntity(invoice);
        session().merge(invoiceEntityToUpdate);
    }
}
