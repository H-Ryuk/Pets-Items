package com.hassan.pets.EventListener;

import com.hassan.pets.Model.Orders;
import com.hassan.pets.Records.DateStatusOrderRecord;
import com.hassan.pets.Repository.OrdersRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Component
@Transactional
public class StartupListener {


    private final OrdersRepo ordersRepo;

    public StartupListener(OrdersRepo ordersRepo) {
        this.ordersRepo = ordersRepo;
    }



    // Confirm purchase if order date is greater than 1 week
    @EventListener(ApplicationReadyEvent.class)
    public void changeOrderStatus(){
        List<DateStatusOrderRecord> recordList = ordersRepo.getOrderDate();

        for(DateStatusOrderRecord r : recordList){
            if (r.orderDate().plusWeeks(1).isBefore(LocalDate.now()) &&
                    r.status().equals(Orders.OrderStatus.PENDING)){

                ordersRepo.changeOrderStatus(r.orderId());
            }
        }
    }


}
