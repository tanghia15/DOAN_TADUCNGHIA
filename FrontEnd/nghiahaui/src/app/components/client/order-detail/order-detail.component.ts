import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderDetail } from 'src/app/_class/order-detail';
import { OrderDetailService } from 'src/app/_service/order-detail.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss']
})
export class OrderDetailComponent implements OnInit {

  orderDetail: OrderDetail[] = [];
  orderId!: number;

  constructor(private route: ActivatedRoute, private orderDetailService: OrderDetailService, private location:Location) { }

  ngOnInit(): void {
    // Lấy orderId từ đường dẫn URL
    this.route.params.subscribe(params => {
      this.orderId = +params['orderId'];
      // Kiểm tra và gọi getOrderDetail() nếu orderId hợp lệ
      if (!isNaN(this.orderId)) {
        this.getOrderDetail();
      } else {
        console.error('Invalid orderId.');
      }
    });
  }

  getOrderDetail(): void {
    this.orderDetailService.getOrderDetailsByOrderId(this.orderId).subscribe({
      next: (data) => {
        this.orderDetail = data;
      },
      error: (error) => {
        console.error('Error fetching order detail:', error);
      }
    });
  }

  goBack(): void {
    this.location.back(); // Trở lại trang trước
  }
}

