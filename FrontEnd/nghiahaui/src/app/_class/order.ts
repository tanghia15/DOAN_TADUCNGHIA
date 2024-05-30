import { OrderDetail } from "./order-detail";
export class Order {
    id!:number;
    firstname !: string;
    lastname !:string;
    country !:string;
    address!:string;
    town!:string;
    state!:string;
    postCode !: number
    email!:string;
    phone!:string;
    note!:string;
    totalPrice !: number;
    orderDetails!: OrderDetail[];
}
