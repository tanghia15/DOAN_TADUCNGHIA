
import { Product } from "./product";
export class OrderDetail {
    id!:number;
    name !: string;
    price !: number;
    quantity !: number;
    subTotal !:number;
    product!:Product;
}
