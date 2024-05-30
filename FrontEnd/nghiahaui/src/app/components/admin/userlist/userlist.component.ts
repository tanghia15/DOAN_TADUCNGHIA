import { MessageService } from "primeng/api";
import { Component, OnInit } from "@angular/core";
import { UserService } from "src/app/_service/user.service";
import { StorageService } from "src/app/_service/storage.service";
import { User } from "src/app/_class/user";
@Component({
    selector: 'app-userlist',
    templateUrl: './userlist.component.html',
    styleUrls: ['./userlist.component.scss'],
    providers: [MessageService]
  
})
export class UserListComponent implements OnInit {
    listUser!:User[];
    
    constructor(private userService: UserService,private storageService: StorageService,private messageService:MessageService){}
  
    ngOnInit(): void {
      this.getListUser();
    }
    getListUser(){
        this.userService.getListUser().subscribe({
          next: res =>{
            this.listUser = res;
            console.log(res);
          },error: err =>{
            console.log(err);
          }
        })
      }
      enableUser(id : number){
        this.userService.enableUser(id).subscribe({
          next: res =>{
            this.getListUser();
            this.showSuccess("Cập nhật thành công!!");
    
          },error: err=>{
            this.showError(err.message);
          }
        })
      }
      showSuccess(text: string) {
        this.messageService.add({severity:'success', summary: 'Success', detail: text});
      }
      showError(text: string) {
        this.messageService.add({severity:'error', summary: 'Error', detail: text});
      }
}