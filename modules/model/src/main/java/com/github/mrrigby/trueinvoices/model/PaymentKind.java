package com.github.mrrigby.trueinvoices.model;

import java.time.LocalDate;

/**
 * @author MrRigby
 */
public enum PaymentKind {

    CASH {
        @Override
        public LocalDate calculatePaymentDate(LocalDate soldDate) {
            return soldDate;
        }
    },

    ONE_WEEK {
        @Override
        public LocalDate calculatePaymentDate(LocalDate soldDate) {
            return soldDate.plusWeeks(1);
        }
    },

    TWO_WEEKS {
        @Override
        public LocalDate calculatePaymentDate(LocalDate soldDate) {
            return soldDate.plusWeeks(2);
        }
    },

    ONE_MONTH {
        @Override
        public LocalDate calculatePaymentDate(LocalDate soldDate) {
            return soldDate.plusMonths(1);
        }
    };

    public abstract LocalDate calculatePaymentDate(LocalDate soldDate);
}
