import { ProductService } from './../../shared/services/product.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  displayedColumns: string[] = ['id', 'name', 'price', 'quantity', 'category', 'picture'];
  dataSource = new MatTableDataSource<ProductElement>();

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  getProducts() {
    this.productService.getProducts()
    .subscribe({
      next: (data: any) => {
        console.log(data)
        this.processProductResponse(data);
      }, error: (error: any) => {
        console.log("error", error);
      }
    })
  }

  processProductResponse(resp : any) {
    const dateProduct : ProductElement [] = [];
      if(resp.metadata[0].code == "00") {
        let listCProduct = resp.product.products;

        listCProduct.forEach((element: ProductElement) => {
            element.category = element.category.name;
            element.picture = 'data:image/jpeg;base64,' +element.picture;
            dateProduct.push(element);
        });

        // set the datasource
        this.dataSource = new MatTableDataSource<ProductElement>(dateProduct);
        this.dataSource.paginator = this.paginator;
      }

  }
}

export interface ProductElement {
  id: number;
  name : string;
  price: number;
  quantity: number;
  category: any;
  picture: any;
}
