package com.shashank.payment.controller;


import com.shashank.comman.entity.GlobalConfigs;
import com.shashank.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")

public class PaymentController {

    private final PaymentService paymentService;
    private final GlobalConfigs globalConfigs;
    public record PaymentSimStatus(boolean success) {}

    public PaymentController(PaymentService paymentService, GlobalConfigs globalConfigs) {
        this.paymentService = paymentService;

        this.globalConfigs = globalConfigs;
    }

    @PutMapping("/status")
    @Operation(
            summary = "Set payment simulation status",
            description = """
                    Controls whether the payment service responds with success or failure
                    for all new orders. Does not require service restart.
                    """
    )

    public ResponseEntity<PaymentSimStatus> setPaymentStatus(
            @Parameter(
                    description = "true = always approve payments, false = always fail payments",
                    example = "true"
            )
            @RequestParam("success") boolean success
    ) {
    globalConfigs.setStatusPayment(success?this.globalConfigs.SUCCESS:this.globalConfigs.FAILURE);
        return ResponseEntity.ok(new PaymentSimStatus(success));
    }

    @GetMapping("/status")
    @Operation(
            summary = "Get current payment simulation status",
            description = "Returns whether payment service is currently in success or failure mode."
    )

    public ResponseEntity<PaymentSimStatus> getPaymentStatus() {
    boolean success=globalConfigs.getStatusPayment()==this.globalConfigs.SUCCESS;
        return ResponseEntity.ok(new PaymentSimStatus(success));
    }
}