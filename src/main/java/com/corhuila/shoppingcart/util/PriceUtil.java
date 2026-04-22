package com.corhuila.shoppingcart.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utilidades para operaciones con precios y descuentos.
 */
public final class PriceUtil {

    private PriceUtil() {}

    private static final BigDecimal THRESHOLD_HIGH   = new BigDecimal("300000");
    private static final BigDecimal THRESHOLD_MEDIUM = new BigDecimal("100000");

    /**
     * Aplica descuento automático según el total:
     *  - >= 300.000 → 15%
     *  - >= 100.000 → 10%
     *  - < 100.000  → sin descuento
     */
    public static BigDecimal applyDiscount(BigDecimal total) {
        if (total.compareTo(THRESHOLD_HIGH) >= 0) {
            return total.multiply(new BigDecimal("0.85")).setScale(2, RoundingMode.HALF_UP);
        } else if (total.compareTo(THRESHOLD_MEDIUM) >= 0) {
            return total.multiply(new BigDecimal("0.90")).setScale(2, RoundingMode.HALF_UP);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Redondea un BigDecimal a 2 decimales.
     */
    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
