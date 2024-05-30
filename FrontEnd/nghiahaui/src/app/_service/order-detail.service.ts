import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrderDetail } from '../_class/order-detail';

const ORDER_DETAIL_API = "http://localhost:8080/api/order/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class OrderDetailService {

  constructor(private http: HttpClient) { }

  getOrderDetailsByOrderId(orderId: number): Observable<OrderDetail[]> {
    const url = `${ORDER_DETAIL_API}${orderId}`;
    return this.http.get<OrderDetail[]>(url);
  }

  // Thêm các phương thức khác cho order_detail nếu cần
}
