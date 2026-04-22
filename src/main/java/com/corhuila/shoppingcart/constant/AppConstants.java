package com.corhuila.shoppingcart.constant;

public final class AppConstants {

    private AppConstants() {}

    // API versioning
    public static final String API_V1 = "/api/v1";

    // Endpoints
    public static final String PRODUCTS_URL   = API_V1 + "/products";
    public static final String CUSTOMERS_URL  = API_V1 + "/customers";
    public static final String CARTS_URL      = API_V1 + "/carts";
    public static final String ORDERS_URL     = API_V1 + "/orders";

    // Mensajes de éxito
    public static final String MSG_CREATED    = "Recurso creado exitosamente";
    public static final String MSG_UPDATED    = "Recurso actualizado exitosamente";
    public static final String MSG_DELETED    = "Recurso eliminado exitosamente";
    public static final String MSG_FOUND      = "Recurso encontrado";
    public static final String MSG_LIST       = "Listado obtenido exitosamente";
    public static final String MSG_CHECKOUT   = "Checkout realizado exitosamente";

    // Mensajes de error
    public static final String MSG_NOT_FOUND          = "Recurso no encontrado";
    public static final String MSG_INSUFFICIENT_STOCK = "Stock insuficiente";
    public static final String MSG_DUPLICATE_EMAIL    = "El email ya está registrado";
    public static final String MSG_CART_EMPTY         = "El carrito está vacío";
    public static final String MSG_CART_PROCESSED     = "El carrito ya fue procesado";
}
