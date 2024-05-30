import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/_service/auth.service';
import { StorageService } from 'src/app/_service/storage.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss'],
  providers: [MessageService]

})
export class LoginPageComponent implements OnInit {


  isSuccessful = false;
  isSignUpFailed = false;
  isLoggedIn = false;
  isLoginFailed = false;
  roles: string[] = [];
  errorMessage = '';

  loginForm : any = {
    username : null,
    password : null
  }

  registerForm : any = {
    username: null,
    email: null,
    password: null
  }

  constructor(private authService:AuthService,private storageService: StorageService,private messageService:MessageService,private router:Router){}

  ngOnInit(): void {
  }

  login(): void {
    const { username, password } = this.loginForm;
    console.log(this.loginForm);
    this.authService.login(username, password).subscribe({
      next: res => {
        this.storageService.saveUser(res);
        this.isLoggedIn = true;
        this.isLoginFailed = false;
        this.roles = this.storageService.getUser().roles;
        this.showSuccess("Đăng nhập thành công!!");
        this.router.navigate(['/']);
      },
      error: err => {
        debugger
        console.log(err);
        this.isLoggedIn = false;
        this.isLoginFailed = true;
        alert("Tài khoản hoặc mật khẩu không chính xác");
        if (err.status === 401 && err.error === "User account is disabled.") {
          alert("Tài khoản của bạn đã bị khóa.");
        } 
        else{
          this.showError("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.");
        }
      }
    });
  }
  

  register():void{
    const {username,email,password} = this.registerForm;
    console.log(this.registerForm);
    this.authService.register(username,email,password).subscribe({
      next: res =>{
        this.isSuccessful = true;
        this.isSignUpFailed = false;
        alert("Đăng ký thành công")
        this.loginForm.username = username;
        this.loginForm.password = password;
        this.login();
      },error: err =>{
        if (err.status === 500) { // 409 là mã lỗi cho tên người dùng đã tồn tại
          this.showUsernameExistsError(); // Hiển thị thông báo tên người dùng đã tồn tại
        } else{
        this.showError(err.message);
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
        
      }
    }
    })
  }
  showUsernameExistsError(): void {
    // Hiển thị thông báo cho người dùng biết rằng tên người dùng đã tồn tại
    // Ví dụ:
    alert("Tên người dùng đã tồn tại. Vui lòng chọn một tên người dùng khác.");
  }
  loginFormChange(){
    document.getElementById('container')?.classList.remove("right-panel-active");
  }
  registerFormChange(){
    document.getElementById('container')?.classList.add("right-panel-active");
  }
  

  showSuccess(text: string) {
    this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
    this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }

}
