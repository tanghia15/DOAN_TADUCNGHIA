import { Component, OnInit } from '@angular/core';
import { faBars, faHeart, faPhone, faRetweet, faShoppingBag } from '@fortawesome/free-solid-svg-icons';
import { MessageService } from 'primeng/api';
import { CartService } from 'src/app/_service/cart.service';
import { ProductService } from 'src/app/_service/product.service';
import { WishlistService } from 'src/app/_service/wishlist.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  providers: [MessageService]

})
export class HomeComponent implements OnInit {
  

  heart = faHeart;
  bag = faShoppingBag;
  retweet = faRetweet;

  listProductNewest : any;
  listProductPrice: any;

  showDepartment = true;


  category_items_response= [
  
    {
      breakpoint: '1024px',
      numVisible: 3,
      numScroll: 3
  },
  {
      breakpoint: '768px',
      numVisible: 2,
      numScroll: 2
  },
  {
      breakpoint: '560px',
      numVisible: 1,
      numScroll: 1
  }
  
]

category_items = [
  {
    id: 1,
    src: 'assets/image/mobile.jpg',
    alt: '',
    title: 'Mobile Phone'
  },
  {
    id: 2,
    src: 'assets/image/laptop.jpg',
    alt: '',
    title: 'Laptop'
  },
  {
    id: 3,
    src: 'assets/image/tablet.jpg',
    alt: '',
    title: 'Tablet'
  },
  {
    id: 4,
    src: 'assets/image/smartwatch.jpg',
    alt: '',
    title: 'Smart Watch'
  },
  {
    id: 5,
    src: 'assets/image/mouse.jpg',
    alt: '',
    title: 'Mouse Computer'
  },
  {
    id: 6,
    src: 'assets/image/keyboards.jpg',
    alt: '',
    title: 'Keyboards'
  },
  {
    id: 7,
    src: 'assets/image/louds.jpg',
    alt: '',
    title: 'Loudspeaker'
  }
] ;

constructor(private productSerive:ProductService,private cartService: CartService, private wishlistService: WishlistService,private messageService: MessageService){}

ngOnInit(): void {
  this.getListProduct();  
}


getListProduct(){
  this.productSerive.getListProductNewest(8).subscribe({
    next: res =>{
      this.listProductNewest = res;
    },error: err =>{
      console.log(err);
    }
  })
  this.productSerive.getListProductByPrice().subscribe({
    next:res =>{
      this.listProductPrice =res;
    },error: err=>{
      console.log(err);
    }
  })
}

addToCart(item: any){
  this.cartService.getItems();
  this.showSuccess("Đã thêm vào giỏ hàng")
  this.cartService.addToCart(item,1);
}

addToWishList(item: any){
  if(!this.wishlistService.productInWishList(item)){
    this.showSuccess("Đã thêm vào danh sách yêu thích")
    this.wishlistService.addToWishList(item);
  }
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
