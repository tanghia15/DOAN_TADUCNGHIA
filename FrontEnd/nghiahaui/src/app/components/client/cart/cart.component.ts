import { Component } from '@angular/core';
import { faBars, faHeart, faPhone, faShoppingBag } from '@fortawesome/free-solid-svg-icons';
import { CartService } from 'src/app/_service/cart.service';
import { MessageService } from 'primeng/api'; // Import the message service for toast notifications

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss'],
  providers: [MessageService] // Add MessageService to providers
})
export class CartComponent {
  heart = faHeart;
  bag = faShoppingBag;
  phone = faPhone;
  bars = faBars;

  showDepartment = false;

  constructor(public cartService: CartService, private messageService: MessageService) {}

  showDepartmentClick() {
    this.showDepartment = !this.showDepartment;
  }

  removeFromCart(item: any) {
    this.cartService.remove(item);
    this.messageService.add({ severity: 'info', summary: 'Item Removed', detail: 'The item has been removed from your cart.' });
  }

  updateQuantity(item: any, event: any) {
    let quantity = Number(event.target.value);
    if (quantity > 0 && quantity <= item.stock) {
      this.cartService.updateCart(item, quantity);
    } else if (quantity > item.stock) {
      this.messageService.add({ severity: 'warn', summary: 'Stock Limit Exceeded', detail: 'The quantity exceeds available stock.' });
      item.quantity = item.stock; // Set to max stock if exceeded
      this.cartService.updateCart(item, item.stock);
    } else {
      item.quantity = 1; // Set to minimum if invalid value entered
      this.cartService.updateCart(item, 1);
    }
  }

  plusQuantity(item: any) {
    let quantity = Number(item.quantity);
    if (quantity < item.stock) {
      this.cartService.updateCart(item, ++quantity);
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Stock Limit Exceeded', detail: 'The quantity exceeds available stock.' });
    }
  }

  subtractQuantity(item: any) {
    if (item.quantity > 1) {
      let quantity = Number(item.quantity);
      this.cartService.updateCart(item, --quantity);
    }
  }
}
