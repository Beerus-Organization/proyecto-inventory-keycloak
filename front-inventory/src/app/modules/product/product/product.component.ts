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
        console.log("resposta produtos:", data);
      }, error: (error: any) => {
        console.log("error", error);
      }
    })
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
