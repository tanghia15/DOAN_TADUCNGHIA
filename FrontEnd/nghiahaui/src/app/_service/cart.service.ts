import { EventEmitter, Injectable } from '@angular/core';
import { InputText } from 'primeng/inputtext';
import { Observable, of, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  items : any[] =[];
  
  totalPrice =0;

  total = 0;

  constructor() { }


  saveCart():void{
    localStorage.setItem('cart_items',JSON.stringify(this.items));
  }
  addToCart(item: any, quantity: number) {
    this.loadCart();
  
    // Kiểm tra xem mục đã tồn tại trong giỏ hàng hay chưa
    const existingItem = this.items.find((res) => res.id === item.id);
    if (!existingItem) {
      // Nếu mục chưa tồn tại, thêm vào giỏ hàng
      item.quantity = quantity;
      item.subTotal = item.quantity * item.price;
      const productId = item.productId; // Lấy product ID từ đối tượng item
      // Thêm productId vào đối tượng item nếu cần thiết
      this.items.push(item);
    } else {
      // Nếu mục đã tồn tại, cập nhật số lượng và tổng cộng
      existingItem.quantity += quantity;
      existingItem.subTotal = existingItem.quantity * existingItem.price;
    }
  
    // Lưu giỏ hàng và tính toán tổng cộng
    this.saveCart();
    this.getTotalPrice();
  }
  
  


  updateCart(item:any,quantity: number){
    this.items.forEach(res =>{
      if(res.id == item.id){
        res.quantity = quantity;
        res.subTotal = res.quantity * res.price;
      }
    })
    this.saveCart();
    this.getTotalPrice();
  }
  

  productInCart(item: any):boolean{
    return this.items.findIndex((x:any) => x.id == item.id) > -1;
  }
  loadCart():void{
    this.items = JSON.parse(localStorage.getItem('cart_items') as any) || [];
    this.getTotalPrice();

  }

  getItems() {
    return this.items;
    this.getTotalPrice();
  }



  getTotalPrice(){
    this.totalPrice = 0;
    this.total = 0;
    this.items.forEach(res =>{
      this.totalPrice += res.subTotal;
      this.total = this.totalPrice;
    })
    return this.totalPrice;
  }

  remove(item: any){
    const index = this.items.findIndex((o:any) => o.id == item.id);
    if(index > -1){
      this.items.splice(index,1);
      this.saveCart();
    }
    this.getTotalPrice();
  }

  clearCart(){
    this.items = [];
    this.getTotalPrice();
    localStorage.removeItem('cart_items');
  }

}
