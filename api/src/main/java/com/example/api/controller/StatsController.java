package com.example.api.controller;

import com.example.api.dto.*;
import com.example.api.model.*;
import com.example.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for getting statistics.
 * <p>
 * Handles HTTP requests related to statistics.
 * </p>
 */
@RestController
@RequestMapping("/api/stats")
public class StatsController {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;

    /**
     * Constructor of the controller, injecting the necessary repositories.
     *
     * @param orderRepository      the order repository
     * @param clientRepository     the client repository
     */
    @Autowired
    public StatsController(OrderRepository orderRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Retrieves appliacation statistics.
     * <p>
     * Mapped to HTTP GET requests for /api/stats.
     * </p>
     *
     * @return ResponseEntity containing a app statistics and HTTP status 200 (OK)
     */
    @GetMapping("")
    public ResponseEntity<?> getDashboardStats() {
        long totalAmountOfOrders = orderRepository.count();
        double totalGross = orderRepository.findAll().stream().mapToDouble(Order::getPrice).sum();
        long totalAmountOfClients = clientRepository.findAll().stream().distinct().count();

        var stats = new StatsDTO();
        stats.totalAmountOfOrders = totalAmountOfOrders;
        stats.totalGross = totalGross;
        stats.totalAmountOfClients = totalAmountOfClients;

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }
    /**
     * Retrieves appliacation sparkline.
     * <p>
     * Mapped to HTTP GET requests for /api/stats/sparkline.
     * </p>
     *
     * @return ResponseEntity containing array of values for sparkline and HTTP status 200 (OK)
     */

    @GetMapping("/sparkline")
    public ResponseEntity<double[]> getSparklineStats() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Double> dailySalesList = orderRepository.findDailySales(sevenDaysAgo);

        double[] dailySalesArray = dailySalesList.stream().mapToDouble(Double::doubleValue).toArray();

        return new ResponseEntity<>(dailySalesArray, HttpStatus.OK);
    }
}
