import { Component, OnInit } from '@angular/core';
import { faBars, faHeart, faPhone, faShoppingBag } from '@fortawesome/free-solid-svg-icons';
import { MessageService } from 'primeng/api';
import { Order } from 'src/app/_class/order';
import { OrderDetail } from 'src/app/_class/order-detail';
import { CartService } from 'src/app/_service/cart.service';
import { OrderService } from 'src/app/_service/order.service';
import { StorageService } from 'src/app/_service/storage.service';
import { ProductService } from 'src/app/_service/product.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  providers: [MessageService]

})
export class CheckoutComponent implements OnInit {
  orderId!:number;
  heart = faHeart;
  bag = faShoppingBag;
  phone = faPhone;
  bars = faBars;
  showDepartment = false;
  order = new Order();
  listOrderDetail: any[] =[];
  username !: string;

  orderForm :any ={
    firstname: null,
    lastname : null,
    country : null,
    addrest : null,
    town : null,
    state : null,
    postCode: null,
    email: null,
    phone: null,
    note: null
  }

  constructor(public cartService: CartService,private orderService:OrderService,private storageService: StorageService,private productService:ProductService, private router:Router){
    
  }
  ngOnInit(): void {
    this.username = this.storageService.getUser().username;
    this.cartService.getItems();
    console.log(this.username);
  }

  showDepartmentClick(){
    this.showDepartment = !this.showDepartment;
  }

  placeOrder() {
    this.cartService.items.forEach(res => {
      let orderDetail: OrderDetail = new OrderDetail; // Pass this.order as an argument
      orderDetail.name = res.name;
      orderDetail.price = res.price;
      orderDetail.quantity = res.quantity;
      orderDetail.subTotal = res.subTotal;
      this.listOrderDetail.push(orderDetail);
    });
    
    const { firstname, lastname, country, address, town, state, postCode, phone, email, note } = this.orderForm;
    this.orderService.placeOrder(firstname, lastname, country, address, town, state, postCode, phone, email, note, this.listOrderDetail, this.username).subscribe({
      next: res => {
        this.cartService.clearCart();
        this.router.navigate(['/payment']);
      },
      error: err => {
        console.log(err);
      }
    });

 
  }
}
