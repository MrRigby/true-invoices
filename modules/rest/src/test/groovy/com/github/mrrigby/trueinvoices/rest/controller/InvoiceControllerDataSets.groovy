package com.github.mrrigby.trueinvoices.rest.controller
/**
 * @author MrRigby
 */
class InvoiceControllerDataSets {

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
                role: "PurchaserItem",
                purchaser_record_number: 0
    }

    public static manyInvoices = {
        invoices id: 1, business_id: "2015/09/01",
                document_date: "2015-10-01", sold_date: "2015-10-01", payment_date: "2015-10-01", payment_kind: "CASH"
        invoices id: 2, business_id: "2015/09/02",
                document_date: "2015-10-02", sold_date: "2015-10-02", payment_date: "2015-10-02", payment_kind: "CASH"
        invoices id: 3, business_id: "2015/09/03",
                document_date: "2015-10-03", sold_date: "2015-10-03", payment_date: "2015-10-03", payment_kind: "CASH"
        invoices id: 4, business_id: "2015/09/04",
                document_date: "2015-10-04", sold_date: "2015-10-04", payment_date: "2015-10-04", payment_kind: "CASH"
        invoices id: 5, business_id: "2015/09/05",
                document_date: "2015-10-05", sold_date: "2015-10-05", payment_date: "2015-10-05", payment_kind: "CASH"
        invoices id: 6, business_id: "2015/09/06",
                document_date: "2015-10-06", sold_date: "2015-10-06", payment_date: "2015-10-06", payment_kind: "CASH"
        invoices id: 7, business_id: "2015/09/07",
                document_date: "2015-10-07", sold_date: "2015-10-07", payment_date: "2015-10-07", payment_kind: "CASH"
    }

}
