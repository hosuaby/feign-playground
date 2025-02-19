package io.hosuaby.feign.playground.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.hosuaby.feign.playground.domain.Bill;
import io.hosuaby.feign.playground.domain.Flavor;
import io.hosuaby.feign.playground.domain.IceCreamOrder;
import io.hosuaby.feign.playground.domain.Mixin;

import java.util.Collection;

@Headers({"Accept: application/json"})
public interface IcecreamClient {

    @RequestLine("GET /icecream/flavors")
    Collection<Flavor> getAvailableFlavors();

    @RequestLine("GET /icecream/mixins")
    Collection<Mixin> getAvailableMixins();

    @RequestLine("POST /icecream/orders")
    @Headers("Content-Type: application/json")
    Bill makeOrder(IceCreamOrder order);

    @RequestLine("GET /icecream/orders/{orderId}")
    IceCreamOrder findOrder(@Param("orderId") int orderId);

    @RequestLine("POST /icecream/bills/pay")
    @Headers("Content-Type: application/json")
    void payBill(Bill bill);
}
