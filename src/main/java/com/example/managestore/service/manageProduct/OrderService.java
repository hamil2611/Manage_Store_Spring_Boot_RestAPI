package com.example.managestore.service.manageProduct;

import com.example.managestore.entity.dto.CustomerDto;
import com.example.managestore.entity.dto.OrderDto;
import com.example.managestore.entity.order.*;
import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.ShoesItem;
import com.example.managestore.enums.OrderStatus;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageProduct.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderShoesRepository orderShoesRepository;
    private final OrderClothesRepository orderClothesRepository;
    private final ClothesItemRepository clothesItemRepository;
    private final ShoesItemRepository shoesItemRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Orders createOrder(OrderDto orderDto){
        Orders order = new Orders();
        List<OrderItem> orderItems = orderDto.getOrderItems();
        Float totalPrice = (float) 0L;
        int totalProduct = 0;
        List<OrderShoes> listOrderShoes = new ArrayList<>();
        List<OrderClothes> listOrderClothes = new ArrayList<>();
        for(OrderItem oi :orderItems){
            if (oi.getCategoryId() == 1){
                ClothesItem clothesItem = clothesItemRepository.findById(oi.getProductId()).orElseThrow(() ->{
                   throw new EntityNotFoundException(String.format("Clothes with id=%f not found", oi.getCategoryId()));
                });
                OrderClothes orderClothes = new OrderClothes();
                orderClothes.setClothesItem(clothesItem);
                orderClothes.setQuantity(oi.getQuantity());
                listOrderClothes.add(orderClothes);
                totalPrice+= oi.getQuantity()*clothesItem.getClothes().getPrice();
            }
            else{
                ShoesItem shoesItem = shoesItemRepository.findById(oi.getProductId()).orElseThrow(()->{
                   throw new EntityNotFoundException(String.format("Shoes with id=%f not found", oi.getCategoryId()));
                });
                OrderShoes orderShoes = new OrderShoes();
                orderShoes.setShoesItem(shoesItem);
                orderShoes.setQuantity(oi.getQuantity());
                listOrderShoes.add(orderShoes);
                totalPrice+=oi.getQuantity()*shoesItem.getShoes().getPrice();
            }
            totalProduct+=oi.getQuantity();
        }
        try{
            Customer customerInserted = customerRepository.save(orderDto.getCustomer());
            order.setCreatedDate(LocalDateTime.now());
            order.setStatus(OrderStatus.UNPAID);
            order.setTotalPrice(totalPrice);
            order.setTotalProduct(totalProduct);
            order.setCustomer(customerInserted);
            Orders orderSaved = orderRepository.save(order);
            listOrderShoes.forEach( x -> x.setOrder(orderSaved));
            orderShoesRepository.saveAll(listOrderShoes);
            listOrderClothes.forEach(x -> x.setOrder(orderSaved));
            orderClothesRepository.saveAll(listOrderClothes);
        }catch (DataAccessException e){
            System.out.println("MESSAGE "+e);
            throw new RepositoryAccessException("Unable save");
        }
        return order;
    }
}
