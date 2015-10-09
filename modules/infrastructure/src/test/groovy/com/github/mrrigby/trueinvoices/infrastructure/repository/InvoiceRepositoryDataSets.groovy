package com.github.mrrigby.trueinvoices.infrastructure.repository
/**
 * @author MrRigby
 */
class InvoiceRepositoryDataSets {

    public static invoiceWithNoDependencies = {
        invoices id: 1,
                business_id: "2015/09/03",
                document_date: "2015-10-01",
                sold_date: "2015-10-01",
                payment_date: "2015-10-01",
                payment_kind: "CASH"
    }

    public static invoiceWithDependencies = {
        invoices id: 1,
                business_id: "2015/09/03",
                document_date: "2015-10-01",
                sold_date: "2015-10-02",
                payment_date: "2015-10-16",
                payment_kind: "TWO_WEEKS"
        invoice_items id: 1,
                invoice_id: 1,
                quantity: 1,
                commodity: "Pruning trees",
                auxiliary_symbol: "SWW/PKWIU/1",
                measure: "item",
                single_net_price: 1499.99,
                tax_rate: 5,
                item_record_number: 0
        invoice_items id: 2,
                invoice_id: 1,
                quantity: 2,
                commodity: "Mowing",
                auxiliary_symbol: "SWW/PKWIU/2",
                measure: "item",
                single_net_price: 499.99,
                tax_rate: 7,
                item_record_number: 1
        invoice_purchasers id: 1,
                invoice_id: 1,
                name: "John Doe Inc.",
                address: "Spitfire Street 12, London",
                tax_id: "1234567890",
                role: "Purchaser",
                purchaser_record_number: 0
    }
}
