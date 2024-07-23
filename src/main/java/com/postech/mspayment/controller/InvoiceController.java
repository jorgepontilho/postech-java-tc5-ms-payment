package com.postech.mspayment.controller;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/create/{customerId}/{cartId}/{amount}")
    @Operation(summary = "Create a new invoice", responses = {
            @ApiResponse(description = "The new invoice was created", responseCode = "201")
    })
    public ResponseEntity<?> create(@PathVariable("customerId") Long customerId,
                                    @PathVariable("cartId") Long cartId,
                                    @PathVariable("amount") BigDecimal amount) {
        try {
            logger.info(" * Creating invoice for customerId: {}, cartId: {}, amount: {}", customerId, cartId, amount);

            InvoiceDTO invoiceCreatedDTO = invoiceService.createInvoice(customerId, cartId, amount);

            return new ResponseEntity<>(invoiceCreatedDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating invoice", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    @Operation(summary = "Get all invoices", responses = {
            @ApiResponse(description = "List of all invoices", responseCode = "200",  content = @Content(schema = @Schema(implementation = InvoiceDTO.class)))
    })
    public ResponseEntity<?> getAllInvoices() {
        try {
            List<Invoice> invoices = invoiceService.getAllInvoices();
            if (invoices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<InvoiceDTO> invoiceDTOs = invoices.stream()
                    .map(Invoice::toInvoiceDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(invoiceDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get only invoice by ID", responses = {
            @ApiResponse(description = "The invoice by ID", responseCode = "200", content = @Content(schema = @Schema(implementation = InvoiceDTO.class))),
            @ApiResponse(description = "Invoice Not Found", responseCode = "404", content = @Content(schema = @Schema(type = "string", example = "Invoice not found.")))
    })
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(invoice -> new ResponseEntity<>(invoice.toInvoiceDTO(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //Nao pode update uma invoice

    //soft delete
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an invoice by ID", responses = {
            @ApiResponse(description = "The invoice was deleted", responseCode = "204"),
            @ApiResponse(description = "The invoice was not found", responseCode = "404")
    })
    public ResponseEntity<HttpStatus> deleteInvoice(@PathVariable Long id) {
        try {
            invoiceService.deleteInvoice(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

