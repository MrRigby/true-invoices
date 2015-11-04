
    create table invoice_items (
        id bigint generated by default as identity,
        auxiliary_symbol varchar(20),
        commodity varchar(250) not null,
        measure varchar(20) not null,
        quantity integer not null,
        single_net_price numeric(19,2) not null,
        tax_rate smallint not null,
        invoice_id bigint not null,
        item_record_number integer,
        primary key (id)
    );

    create table invoice_purchasers (
        id bigint generated by default as identity,
        address varchar(200),
        name varchar(100),
        tax_id varchar(10),
        role varchar(255),
        invoice_id bigint not null,
        purchaser_record_number integer,
        primary key (id)
    );

    create table invoices (
        id bigint generated by default as identity,
        business_id varchar(50) not null,
        document_date date not null,
        payment_date date not null,
        payment_kind varchar(255),
        sold_date date not null,
        primary key (id)
    );

    create table purchasers (
        id bigint generated by default as identity,
        address varchar(200),
        name varchar(100),
        tax_id varchar(10),
        primary key (id)
    );

    alter table invoice_items 
        add constraint FK_31mgw3csfum15wwfuekqegbmv 
        foreign key (invoice_id) 
        references invoices;

    alter table invoice_purchasers 
        add constraint FK_ml9ihhyo5qjx3lgkw63sioae7 
        foreign key (invoice_id) 
        references invoices;
