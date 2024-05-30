import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from '../_class/order';
import { OrderDetail } from '../_class/order-detail';

const ORDER_API = "http://localhost:8080/api/order/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  constructor(private http: HttpClient) { }

  getListOrder():Observable<any>{
    return this.http.get(ORDER_API,httpOptions);
  }


  getListOrderByUser(username: string):Observable<any>{
    let params = new HttpParams();
    params = params.append('username',username);
    return this.http.get(ORDER_API + 'user',{params: params});

  }

  placeOrder(firstname: string,lastname:string,country:string,address: string,town: string,state:string,postCode: string,phone:string,email:string,note:string,orderDetails: OrderDetail[],username: string):Observable<any>{
    return this.http.post(ORDER_API +'create',{firstname,lastname,country,address,town,state,postCode,phone,email,note,orderDetails,username},httpOptions);
  }
  getOrderDetail(orderId: number): Observable<OrderDetail> {
    return this.http.get<OrderDetail>(`${ORDER_API}${orderId}`);
  }
  getOrderDetailsByOrderId(orderId: number): Observable<OrderDetail[]> {
    const url = `${ORDER_API}${orderId}`; // Địa chỉ URL để lấy chi tiết đơn hàng dựa trên orderId
    return this.http.get<OrderDetail[]>(url);
  }
  updateOrderStatus(orderId: number, status: string): Observable<any> {
    const url = `${ORDER_API}${orderId}/status`; // Assuming the endpoint for updating order status is /api/order/{orderId}/status
    return this.http.put(url, { status }, httpOptions);
  }
 
}
