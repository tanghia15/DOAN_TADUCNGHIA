import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from 'src/app/_service/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  listOrder : any;

  constructor(private orderService: OrderService,private router:Router){

  }

  ngOnInit(): void {
    this.getListOrder();
  }


  getListOrder(){
    this.orderService.getListOrder().subscribe({
      next: res=>{
        this.listOrder = res;
        console.log(this.listOrder);
      },error: err =>{
        console.log(err);
      }
    })
  }
  viewOrderDetail(orderId: number): void {
    this.router.navigate(['/admin/order', orderId]);
  }
  
  // deleteOrder(){
  //   const {id} = this.tagForm;
  //   this.tagService.deleteTag(id).subscribe({
  //     next: res =>{
  //       this.getList();
  //       this.showWarn("Xóa danh mục thành công!!");
  //       this.deleteForm = false;
  //     },error: err=>{
  //       this.showError(err.message);
  //     }
  //   })
  // }

}
