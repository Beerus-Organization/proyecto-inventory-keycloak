import { CategoryService } from './../../../shared/services/category.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.getCategories();

  }

  getCategories() {
    this.categoryService.getCategories().subscribe({
    next: (data:any) => {
      console.log("respusta",data)
    },
    error: (error:any) => {
      console.log("respusta", error)
    }
  })
  }
}
