import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
const PAYMENT_API = "http://localhost:8080/api/order/payment";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})

export class PaymentService {
  
  constructor(private http: HttpClient) { }
  createPayment(orderId: number): Observable<any> {
    // Gửi yêu cầu GET tới API để tạo thanh toán
    const url = `${PAYMENT_API}/create_payment`;
    const params = new HttpParams().set('orderId', orderId.toString());

    return this.http.get(url, { params: params });
  }
}
