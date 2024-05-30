import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/_service/order.service';
import { StorageService } from 'src/app/_service/storage.service';
import { Router } from '@angular/router';
import { PaymentService } from 'src/app/_service/payment.service';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-my-order',
  templateUrl: './my-order.component.html',
  styleUrls: ['./my-order.component.css']
})
export class MyOrderComponent implements OnInit {

  listOrder:any;
  username: any;
  constructor(private orderService: OrderService,private storageService: StorageService,private router: Router, private paymentService:PaymentService, private messageService:MessageService){}

  ngOnInit(): void {
    this.username = this.storageService.getUser().username;
    this.getListOrder();
  }

  getListOrder(){
    this.orderService.getListOrderByUser(this.username).subscribe({
      next: res=>{
        this.listOrder = res;
        console.log(this.listOrder);
      },error: err =>{
        console.log(err);
      }
    })
  }
  viewOrderDetail(orderId: number): void {
    this.router.navigate(['/order', orderId]);
  }
  createPayment(orderId: number): void {
    debugger
    // Gọi phương thức từ PaymentService để thực hiện thanh toán với orderId được chuyển vào
    this.paymentService.createPayment(orderId).subscribe({
      next: (response) => {
        // Xử lý kết quả trả về từ phương thức createPayment() nếu cần thiết
        console.log('Payment response:', response);
        // Cập nhật lại danh sách đơn hàng sau khi thanh toán thành công
        this.getListOrder();
        window.location.href = response.url;
      },
      error: (error) => {
        console.error('Error making payment:', error);
        // Hiển thị thông báo lỗi cho người dùng nếu có lỗi xảy ra
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Payment failed!' });
      }
    });
  }
  
}
