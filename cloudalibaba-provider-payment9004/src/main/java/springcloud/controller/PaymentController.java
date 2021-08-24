package springcloud.controller;

import com.chenTest.springcloud.entities.CommonResult;
import com.chenTest.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {

    public static HashMap<Long, Payment> map = new HashMap<>();

    static {
        map.put(1L, new Payment(1L, "test1"));
        map.put(2L, new Payment(2L, "test2"));
        map.put(3L, new Payment(3L, "test3"));

    }

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        CommonResult<Payment> result = new CommonResult<>(200, "from MYSQL, " +
                "serverPort: " + serverPort, payment);
        return result;
    }

}
