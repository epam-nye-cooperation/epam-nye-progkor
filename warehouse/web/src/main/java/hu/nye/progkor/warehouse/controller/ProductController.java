package hu.nye.progkor.warehouse.controller;

import hu.nye.progkor.warehouse.model.dto.ProductDTO;
import hu.nye.progkor.warehouse.model.exception.NotFoundException;
import hu.nye.progkor.warehouse.model.exception.ProductUpdateException;
import hu.nye.progkor.warehouse.model.request.ProductRequest;
import hu.nye.progkor.warehouse.model.response.ProductResponse;
import hu.nye.progkor.warehouse.service.ProductService;
import java.util.List;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private static final String SUCCESS_ATTRIBUTE = "success";
    private static final String PRODUCT_ATTRIBUTE = "product";
    private static final String MESSAGE_ATTRIBUTE = "message";
    public static final String REDIRECT_PRODUCTS_LIST_HTML_ENDPOINT = "redirect:/products/list.html";

    private final ProductService productService;
    private final Converter<ProductDTO, ProductResponse> productDtoToResponseConverter;
    private final Converter<ProductRequest, ProductDTO> productRequestProductDTOConverter;

    @GetMapping(path = "/create.html")
    public String productCreateForm(final Model model) {
        log.info("Visit Product create form page.");
        return "products/create";
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createProduct(final Model model,
                                final ProductRequest productRequest) {
        log.info("Create Product with name:{}.", productRequest.getName());
        final ProductResponse product = productDtoToResponseConverter.convert(
                productService.createProduct(
                        productRequestProductDTOConverter.convert(productRequest)
                )
        );
        model.addAttribute(SUCCESS_ATTRIBUTE, true);
        model.addAttribute(PRODUCT_ATTRIBUTE, product);
        return "products/create.html";
    }

    @GetMapping(path = "/list.html")
    public String getProducts(final Model model) {
        log.info("Retrieve all Products.");
        final List<ProductResponse> products = productService.getAllProducts().stream()
                .map(productDtoToResponseConverter::convert)
                .toList();
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping(path = "/{id}/edit.html")
    public String productEditForm(final RedirectAttributes redirectAttributes, final Model model, final @PathVariable("id") Long id) {
        log.info("Load Update form for Product with ID:{}.", id);
        try {
            final ProductDTO product = productService.getProduct(id);
            model.addAttribute(PRODUCT_ATTRIBUTE, product);
            return "products/edit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, false);
            redirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, "There is no Product with ID:" + id);
            return REDIRECT_PRODUCTS_LIST_HTML_ENDPOINT;
        }
    }

    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editProduct(final RedirectAttributes redirectAttributes,
                              final Model model,
                              final @RequestParam(value = "id", required = false) Long id,
                              final ProductRequest productRequest
    ) {
        log.info("Update Product with ID:{}. {}", id, productRequest);
        try {
            final ProductResponse product = productDtoToResponseConverter.convert(
                    productService.updateProduct(id,
                            productRequestProductDTOConverter.convert(productRequest))
            );
            model.addAttribute(PRODUCT_ATTRIBUTE, product);
            return "products/edit";
        } catch (NotFoundException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, "Nem létezik a termék, ID:" + id);
        } catch (ProductUpdateException e) {
            redirectAttributes.addFlashAttribute(MESSAGE_ATTRIBUTE, "Nem lehetett módosítani a terméket, mert már raktárhoz van rendelve, Termék azonosító:" + id);
        }
        redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, false);
        return REDIRECT_PRODUCTS_LIST_HTML_ENDPOINT;
    }

    @GetMapping(path = "/remove/{id}")
    public String removeProduct(final RedirectAttributes redirectAttributes, final @PathVariable("id") Long id) {
        log.info("Remove a Product with ID: {}.", id);
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, true);
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, false);
        }
        return REDIRECT_PRODUCTS_LIST_HTML_ENDPOINT;
    }
}
