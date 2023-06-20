package com.example.managestore.service.manageProduct;

import com.example.managestore.domain.Grid;
import com.example.managestore.entity.dto.CustomerDto;
import com.example.managestore.entity.dto.OrderItemDto;
import com.example.managestore.entity.order.*;
import com.example.managestore.entity.product.clothes.ClothesItem;
import com.example.managestore.entity.product.shoes.ShoesItem;
import com.example.managestore.enums.Constants;
import com.example.managestore.enums.OrderStatus;
import com.example.managestore.exception.entityException.EntityNotFoundException;
import com.example.managestore.exception.entityException.RepositoryAccessException;
import com.example.managestore.repository.manageProduct.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
    private final ModelMapper modelMapper;

    @Transactional
    public Orders createOrder(OrderItemDto orderItemDto) {
        Orders order = new Orders();
        List<OrderItem> orderItems = orderItemDto.getOrderItems();
        Float totalPrice = (float) 0L;
        int totalProduct = 0;
        List<OrderShoes> listOrderShoes = new ArrayList<>();
        List<OrderClothes> listOrderClothes = new ArrayList<>();
        for (OrderItem oi : orderItems) {
            if (oi.getCategoryId() == 1) {
                ClothesItem clothesItem = clothesItemRepository.findById(oi.getProductId()).orElseThrow(() -> {
                    throw new EntityNotFoundException(Constants.CLOTHES_NOT_FOUND + oi.getProductId());
                });
                OrderClothes orderClothes = new OrderClothes();
                orderClothes.setClothesItem(clothesItem);
                orderClothes.setQuantity(oi.getQuantity());
                listOrderClothes.add(orderClothes);
                totalPrice += oi.getQuantity() * clothesItem.getClothes().getPrice();
            } else {
                ShoesItem shoesItem = shoesItemRepository.findById(oi.getProductId()).orElseThrow(() -> {
                    throw new EntityNotFoundException(Constants.SHOES_NOT_FOUND + oi.getProductId());
                });
                OrderShoes orderShoes = new OrderShoes();
                orderShoes.setShoesItem(shoesItem);
                orderShoes.setQuantity(oi.getQuantity());
                listOrderShoes.add(orderShoes);
                totalPrice += oi.getQuantity() * shoesItem.getShoes().getPrice();
            }
            totalProduct += oi.getQuantity();
        }
        try {
            Customer customer = orderItemDto.getCustomer();
            customer.setCreatedDate(LocalDateTime.now());
            Customer customerInserted = customerRepository.save(customer);
            order.setCreatedDate(LocalDateTime.now());
            order.setStatus(OrderStatus.UNPAID);
            order.setTotalPrice(totalPrice);
            order.setTotalProduct(totalProduct);
            order.setCustomer(customerInserted);
            Orders orderSaved = orderRepository.save(order);
            listOrderShoes.forEach(x -> x.setOrder(orderSaved));
            orderShoesRepository.saveAll(listOrderShoes);
            listOrderClothes.forEach(x -> x.setOrder(orderSaved));
            orderClothesRepository.saveAll(listOrderClothes);
        } catch (DataAccessException e) {
            log.error(Constants.UNABLE_SAVE_RECORD);
            throw new RepositoryAccessException(Constants.UNABLE_SAVE_RECORD);
        }
        return order;
    }
    public Orders getOrder(Long orderId){
        Orders order = orderRepository.findById(orderId).orElseThrow(()->{
            throw new EntityNotFoundException(Constants.ENTITY_NOT_FOUND);
        });
        return order;
    }
    public Page<CustomerDto> getAllCustomer(int page, int size, Grid grid){
        if(StringUtils.isBlank(grid.getGridName()))
            grid.setGridName("id");
        Pageable pageable = PageRequest.of(page,size,grid.getSort().equals("asc")
                ? Sort.by(grid.getGridName()).ascending()
                :Sort.by(grid.getGridName()).descending());
        return customerRepository.findAll(pageable).map(customer -> modelMapper.map(customer,CustomerDto.class));
    }
    public CustomerDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            throw new EntityNotFoundException(Constants.CUSTOMER_NOT_FOUND + customerId);
        });
        return modelMapper.map(customer, CustomerDto.class);
    }
}
