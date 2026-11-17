package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.ShoppingCartDoesNotContainItemException;
import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ShoppingCartItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

public class ShoppingCartTest {

    @Test
    void givenCustomer_whenStartShopping_shouldInitializeEmptyCart() {

        CustomerId customerId = new CustomerId();
        ShoppingCart cart = ShoppingCart.startShopping(customerId);

        Assertions.assertWith(cart,
                c -> Assertions.assertThat(c.id()).isNotNull(),
                c -> Assertions.assertThat(c.customerId()).isEqualTo(customerId),
                c -> Assertions.assertThat(c.totalAmount()).isEqualTo(Money.ZERO),
                c -> Assertions.assertThat(c.totalItems()).isEqualTo(Quantity.ZERO),
                c -> Assertions.assertThat(c.isEmpty()).isTrue(),
                c -> Assertions.assertThat(c.items()).isEmpty()
        );
    }

    @Test
    void givenEmptyCart_whenAddNewItem_shouldContainItemAndRecalculateTotal() {

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(2));

        Assertions.assertThat(cart.items()).hasSize(1);
        ShoppingCartItem item = cart.items().iterator().next();
        Assertions.assertThat(item.productId()).isEqualTo(product.id());
        Assertions.assertThat(item.quantity()).isEqualTo(new Quantity(2));
        Assertions.assertThat(cart.totalItems()).isEqualTo(new Quantity(2));
        Assertions.assertThat(cart.totalAmount()).isEqualTo(new Money(product.price().value().multiply(new BigDecimal(2))));
    }

    @Test
    void givenCartWithExistingProduto_whenAddSameProduct_shoulIncrementQuantity() {

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(3));
        ShoppingCartItem existing = cart.items().iterator().next();

        Set<ShoppingCartItem> items = cart.items();
        Assertions.assertThat(items).hasSize(1);
        Assertions.assertThat(existing.quantity()).isEqualTo(new Quantity(3));

        cart.addItem(product, new Quantity(3));
        existing = cart.items().iterator().next();

        Assertions.assertThat(items).hasSize(1);
        Assertions.assertThat(existing.quantity()).isEqualTo(new Quantity(6));
    }

    @Test
    void givenCartWitItems_whenRemoveEixstingItem_shouldRemoveAndRecalculateTotals() {

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        ShoppingCartItem item = cart.items().iterator().next();

        Assertions.assertThat(cart.items()).hasSize(1);
        Assertions.assertThat(cart.totalItems()).isEqualTo(
                new Quantity(cart.items().stream().mapToInt(i -> i.quantity().value()).sum())
        );

        cart.removeItem(item.id());
        Assertions.assertThat(cart.items()).hasSize(0);
        Assertions.assertThat(cart.items()).doesNotContain(item);
        Assertions.assertThat(cart.totalItems()).isEqualTo(
                new Quantity(cart.items().stream().mapToInt(i -> i.quantity().value()).sum())
        );
    }

    @Test
    void givenCartWithItems_whenRemoveNonExistingItem_shouldThrowShoppingCartDoesNotContainItemException() {

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        ShoppingCartItemId itemId = new ShoppingCartItemId();

        Assertions.assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class)
                .isThrownBy(() -> cart.removeItem(itemId));
    }

    @Test
    void givenCartWithItems_whenEmpty_shouldClearAllItemsAndResetTotal() {

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();
        Product product = ProductTestDataBuilder.aProduct().build();

        cart.addItem(product, new Quantity(1));

        Assertions.assertWith(cart,
                c -> Assertions.assertThat(c.isEmpty()).isFalse(),
                c -> Assertions.assertThat(c.items()).hasSize(1),
                c -> Assertions.assertThat(c.totalItems()).isEqualTo(new Quantity(1)),
                c -> Assertions.assertThat(c.totalAmount()).isEqualTo(new Money(product.price().value().multiply(new BigDecimal(1))))
        );

        cart.empty();

        Assertions.assertWith(cart,
                c -> Assertions.assertThat(c.isEmpty()).isTrue(),
                c -> Assertions.assertThat(c.totalItems()).isEqualTo(Quantity.ZERO),
                c -> Assertions.assertThat(c.totalAmount()).isEqualTo(Money.ZERO)
        );
    }

    @Test
    void givenCartWithItems_whenChangeItemPrice_shouldRecalculateTotalAmount() {

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().withItems(false).build();

        Product product = ProductTestDataBuilder.aProduct().build();
        cart.addItem(product, new Quantity(2));

        ShoppingCartItem item = cart.findItem(product.id());

        Assertions.assertThat(item.price()).isEqualTo(new Money("3000"));
        Assertions.assertThat(cart.totalAmount()).isEqualTo(new Money("6000"));

        product = ProductTestDataBuilder.aProduct()
                .price(new Money("100"))
                .build();

        cart.refreshItem(product);

        item = cart.findItem(product.id());

        Assertions.assertThat(item.price()).isEqualTo(new Money("100"));
        Assertions.assertThat(cart.totalAmount()).isEqualTo(new Money("200"));
    }

    @Test
    void givenCartWithItems_whenDetectUnavailableItems_shoulReturnTrue(){

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        Product product = ProductTestDataBuilder.aProduct().inStock(false).build();

        cart.refreshItem(product);

        Assertions.assertThat(cart.containsUnavailableItems()).isTrue();
    }

    @Test
    void givenCartWithItems_whenChangeQuntityToZero_shouldThrowIllegalArgumentException(){

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().build();
        var item = cart.items().iterator().next();

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> cart.changeItemQuantity(item.id(), Quantity.ZERO));
    }

    @Test
    void givenCartWithItems_whenChangeItemQuantity_shouldRecalculateTotalItemns(){

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        var item = cart.items().iterator().next();
        cart.changeItemQuantity(item.id(), new Quantity(2));
        Assertions.assertThat(cart.totalItems()).isEqualTo(new Quantity(cart.items().stream().mapToInt(i -> i.quantity().value()).sum()));

        cart.changeItemQuantity(item.id(), new Quantity(5));
        Assertions.assertThat(cart.totalItems()).isEqualTo(new Quantity(cart.items().stream().mapToInt(i -> i.quantity().value()).sum()));
    }

    @Test
    void givenCartWithItems_whenFindItemById_shouldReturnItem(){

        ShoppingCart cart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        var item = cart.items().iterator().next();
        var found = cart.findItem(item.id());

        Assertions.assertThat(found).isEqualTo(item);
    }

    @Test
    void givenDifferentIds_whenCompareItems_shouldNotBeEqaul(){

        ShoppingCart cart1 = ShoppingCartTestDataBuilder.aShoppingCart().build();
        ShoppingCart cart2 = ShoppingCartTestDataBuilder.aShoppingCart().build();

        Assertions.assertThat(cart1).isNotEqualTo(cart2);
    }
}
