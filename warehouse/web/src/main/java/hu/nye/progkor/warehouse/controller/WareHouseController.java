package hu.nye.progkor.warehouse.controller;

import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.dto.WareHouseDTO;
import hu.nye.progkor.warehouse.model.exception.NoStorageException;
import hu.nye.progkor.warehouse.model.exception.NotEnoughWareHouseCapacityException;
import hu.nye.progkor.warehouse.model.exception.NotFoundException;
import hu.nye.progkor.warehouse.model.response.ProductResponse;
import hu.nye.progkor.warehouse.model.response.WareHouseResponse;
import hu.nye.progkor.warehouse.service.ProductService;
import hu.nye.progkor.warehouse.service.WareHouseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/warehouses")
public class WareHouseController {

    private final WareHouseService wareHouseService;
    private final ProductService productService;
    private final Converter<ProductDTO, ProductResponse> productDtoToResponseConverter;
    private final Converter<WareHouseDTO, WareHouseResponse> wareHouseDTOWareHouseResponseConverter;

    @GetMapping("/list.html")
    public ModelAndView getWareHouses() {
        log.info("Get WareHouses.");
        final ModelAndView warehouseListView = new ModelAndView("warehouses/list");
        final List<WareHouseResponse> wareHouses = wareHouseService.getWareHouses().stream()
                .map(wareHouseDTOWareHouseResponseConverter::convert)
                .toList();
        warehouseListView.addObject("warehouses", wareHouses);
        return warehouseListView;
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<WareHouseResponse> getWarehouse(final @PathVariable Long id) {
        log.info("Get a Warehouse with ID:{}", id);
        try {
            final WareHouseDTO wareHouse = wareHouseService.getWareHouse(id);
            final WareHouseResponse wareHouseResponse = wareHouseDTOWareHouseResponseConverter.convert(wareHouse);
            return new ResponseEntity<>(wareHouseResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/{id}/assign.html")
    public String loadAssignProductToWareHouse(final Model model,
                                               final @PathVariable("id") Long id) {
        log.info("Loads Product assigning form to Warehouse, Product ID:{}", id);
        final ProductResponse product = productDtoToResponseConverter.convert(productService.getProduct(id));
        final List<WareHouseResponse> wareHouses = wareHouseService.getWareHouses().stream()
                .map(wareHouseDTOWareHouseResponseConverter::convert)
                .toList();
        model.addAttribute("warehouses", wareHouses);
        model.addAttribute("product", product);
        return "warehouses/assign";
    }

    @PostMapping("/products/assign")
    public String assignProductToWareHouse(final RedirectAttributes redirectAttributes,
                                           final @RequestParam(value = "productId", required = false) Long productId,
                                           final @RequestParam(value = "warehouseId", required = false) Long warehouseId,
                                           final @RequestParam(value = "quantity", required = false) Long quantity) {
        log.info("Assign {} pieces Products:{} to Warehouse:{}", quantity, productId, warehouseId);
        try {
            wareHouseService.moveProductsToWareHouse(warehouseId, productId, quantity);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("message", "Sikeres termék hozzárendelés.");
            return "redirect:/warehouses/list.html";
        } catch (NotFoundException | NotEnoughWareHouseCapacityException | NoStorageException e) {
            redirectAttributes.addFlashAttribute("success", false);
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/products/list.html";
        }
    }
}
