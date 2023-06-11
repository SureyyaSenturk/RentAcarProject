package com.bilgeadam.rentacar.invoice;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends BaseRepository<Invoice, String> {

    List<Invoice> findInvoicesByRentalCar_Id(String id);

    Invoice findInvoiceByNumber(Integer invoiceNumber);
}
