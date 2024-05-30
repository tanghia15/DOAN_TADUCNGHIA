package com.example.nghiahaui.model.request;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailRequest {

    @NotNull(message="Tên sản phẩm rỗng")
    @NotEmpty(message = "Tên sản phẩm rỗng")
    @Size(min=5,max=50,message="Tên sản phẩm từ 5-50 ký tự")
    private String name;

    @NotNull(message="Giá sản phẩm rỗng")
    @NotEmpty(message="Giá sản phẩm rỗng")
    @Size(min=0,message ="Giá sản phẩm từ 0 trở lên")
    private long price;

    @NotNull(message = "Số lượng sản phẩm rỗng")
    @NotEmpty(message = "Số lượng sản phẩm rỗng")
    @Size(min = 1,message="Số lượng sản phẩm từ 1 trở lên")
    private int quantity;

    private long subTotal;

//
//    private long productId; // Trường mới để lưu productId//    @NotNull(message = "product_id rỗng")

}
