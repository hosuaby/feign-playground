package io.hosuaby.feign.playground.server;

import io.hosuaby.feign.playground.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/icecream")
public class IcecreamController {
    private final OrderGenerator orderGenerator = new OrderGenerator();

    @GetMapping("/flavors")
    public Collection<Flavor> getAvailableFlavors() {
        return List.of(Flavor.values());
    }

    @GetMapping("/mixins")
    public Collection<Mixin> getAvailableMixins() {
        return List.of(Mixin.values());
    }

    @PostMapping("/orders")
    public Bill makeOrder(@RequestBody IceCreamOrder order) {
        return Bill.makeBill(order);
    }

    @GetMapping("/orders/{orderId}")
    public IceCreamOrder findOrder(@PathVariable int orderId) {
        return orderGenerator.generate();
    }

    @PostMapping("/bills/pay")
    public void payBill(@RequestBody Bill bill) {
    }
}
