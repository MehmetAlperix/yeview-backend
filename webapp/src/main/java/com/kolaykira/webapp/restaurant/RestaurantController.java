package com.kolaykira.webapp.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/restaurant")
@AllArgsConstructor
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/addRestaurant")
    public String addContract(@RequestBody RestaurantRequest request) throws ExecutionException, InterruptedException
    {
        return restaurantService.addRestaurant( RestaurantRequestToRestaurant.requestToContract(request) );
    }

    @DeleteMapping(path = "{restaurant_id}")
    public String deleteContract(@PathVariable("restaurant_id") String contractId) throws ExecutionException, InterruptedException {
        return restaurantService.deleteComment(contractId);
    }
    @GetMapping
    public ResponseEntity<List<Restaurant>> getComments() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(restaurantService.getRestaurants());

    }
    @GetMapping(path = "{restaurant_id}")
    public Restaurant getContractById(@PathVariable(name = "restaurant_id") String contractId) throws ExecutionException, InterruptedException {
        return restaurantService.getCommentsById(contractId);
    }
    @GetMapping(path = "owner/{owner}")
    public List<Restaurant> getCommentsByUser(@PathVariable(name = "owner") String owner) throws ExecutionException, InterruptedException {
        return restaurantService.getRestaurantByOwner(owner);
    }
}