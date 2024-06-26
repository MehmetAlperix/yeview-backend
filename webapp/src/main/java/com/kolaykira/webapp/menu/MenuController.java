package com.kolaykira.webapp.menu;

import com.kolaykira.webapp.comment.CommentService;
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
    private final CommentService commentService;

    @PostMapping("/addmenu")
    public String addContract(@RequestBody MenuRequest request) throws ExecutionException, InterruptedException
    {
        return menuService.addMenu( MenuRequestToRequest.requestToContract(request) );
    }

    @DeleteMapping(path = "{menu_id}")
    public String deleteContract(@PathVariable("menu_id") String id) throws ExecutionException, InterruptedException {
        deleteMenusByRestaurant(id);
        return menuService.deleteMenu(id);
    }
    @GetMapping(path = "get/")
    public ResponseEntity<List<Menu>> getMenu() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(menuService.getMenu());

    }
    @GetMapping(path = "get/{menuID}")
    public Menu getContractById(@PathVariable(name = "menuID") String menuID) throws ExecutionException, InterruptedException {
        return menuService.getMenuById(menuID);
    }
    @GetMapping(path = "get/restaurantsmenus/{restaurantID}")
    public List<Menu> getCommentsByUser(@PathVariable(name = "restaurantID") String restaurantID) throws ExecutionException, InterruptedException {
        return menuService.getMenusByRestaurant(restaurantID);
    }
    @PostMapping("/editmenu")
    public String addContract(@RequestBody MenuEditRequest request) throws ExecutionException, InterruptedException
    {
        return menuService.editMenu( request );
    }


    public void deleteMenusByRestaurant(String contractId) throws ExecutionException, InterruptedException {
        List<Menu> menus = menuService.getMenusByRestaurant(contractId);
        commentService.deleteCommentByMenuID(contractId);
        if(menus != null)
        {
            for(int i = 0; i< menus.size();i++)
            {
                commentService.deleteCommentByMenuID( menus.get(i).getMenuID() );
                menuService.deleteMenu(menus.get(i).getMenuID());
            }
        }
    }
}
