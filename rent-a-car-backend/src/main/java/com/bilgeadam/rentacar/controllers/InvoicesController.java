package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.response.InvoiceResponse;
import com.bilgeadam.rentacar.service.InvoiceService;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoicesController")
@CrossOrigin
public class InvoicesController {

  private final InvoiceService invoiceService;

  public InvoicesController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/getAll")
  DataResult<List<InvoiceResponse>> getAll() {
    return this.invoiceService.getAll();
  }

  @GetMapping("/getAllInvoicesByRentalCarId")
  DataResult<List<InvoiceResponse>> getAllInvoicesByRentalCarId(@RequestParam String id) {
    return this.invoiceService.getAllInvoicesByRentalCarId(id);
  }

  @GetMapping("/getById")
  DataResult<InvoiceResponse> getById(@RequestParam String id) {
    return this.invoiceService.getById(id);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestParam String id) {
    return this.invoiceService.delete(id);
  }
}
