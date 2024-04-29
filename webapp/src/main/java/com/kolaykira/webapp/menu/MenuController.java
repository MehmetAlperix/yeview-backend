package com.kolaykira.webapp.menu;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/menu")
@AllArgsConstructor
@CrossOrigin
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/addmenu")
    public String addContract(@RequestBody MenuRequest request) throws ExecutionException, InterruptedException
    {
        return menuService.addMenu( MenuRequestToRequest.requestToContract(request) );
    }

    @DeleteMapping(path = "{menu_id}")
    public String deleteContract(@PathVariable("menu_id") String id) throws ExecutionException, InterruptedException {
        return menuService.deleteMenu(id);
    }
    @GetMapping
    public ResponseEntity<List<Menu>> getMenu() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(menuService.getMenu());

    }
    @GetMapping(path = "{menuID}")
    public Menu getContractById(@PathVariable(name = "menuID") String menuID) throws ExecutionException, InterruptedException {
        return menuService.getMenuById(menuID);
    }
    @GetMapping(path = "restaurantsmenus/{restaurantID}")
    public List<Menu> getCommentsByUser(@PathVariable(name = "restaurantID") String restaurantID) throws ExecutionException, InterruptedException {
        return menuService.getMenusByRestaurant(restaurantID);
    }

}
