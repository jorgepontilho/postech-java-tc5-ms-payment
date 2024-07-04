package com.postech.mspayment.usecase;

import com.postech.mspayment.entity.Invoice;
import com.postech.mspayment.entity.InvoiceDTO;
import com.postech.mspayment.entity.ProductItem;
import com.postech.mspayment.entity.ProductItemDTO;
import com.postech.mspayment.repository.InvoiceRepository;
import com.postech.mspayment.service.CustomerService;
import com.postech.mspayment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CreateInvoiceUseCase {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerService customerService;

    //@Autowired
    //    private BasketService basketService;

    public InvoiceDTO execute(Long customerId, Long basketId, BigDecimal amount) throws Exception {

        logger.info(" * Creating invoice use case ");
        // Recupera as informacoes do usuario
        CustomerService.UserDTO user = customerService.getCustomerById(customerId);

        String customerName = user.getName();

        // Cria a Invoice
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customerId);
        invoice.setCustomerName(customerName);
        invoice.setTotalAmount(amount);
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());
        Invoice invoiceSaved = invoiceRepository.save(invoice);

        return invoiceSaved.toInvoiceDTO();

        //Recupera as informacoes do carrinho
        //List<ProductItemDTO> products = basketService.getProductsByBasketId(basketId);
        //// Retrieve basket data
        //        Basket basket = restTemplate.getForObject("http://localhost:8083/api/baskets/" + basketId, Basket.class);
        //        if (basket == null) {
        //            throw new Exception("Error retrieving basket data");
        //        }

        // Add os ProductItems
        //for (ProductItemDTO product : products) {
        //            product.setInvoiceId(invoiceSaved.getId());
        //
        //            ProductItem productItem = new ProductItem(product);
        //
        //            invoiceSaved.getProducts().add(productItem);
        //        }

        //invoiceRepository.save(invoiceSaved);
    }
}

