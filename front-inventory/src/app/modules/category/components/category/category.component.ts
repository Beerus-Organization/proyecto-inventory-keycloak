import { NewCategoryComponent } from './../new-category/new-category.component';
import { CategoryService } from './../../../shared/services/category.service';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { elementAt } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  constructor(private categoryService: CategoryService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.getCategories();
  }

  displayedColumns: string[] = ['id', 'name', 'description', 'actions'];
  dataSource = new MatTableDataSource<CategoryElement>();

  getCategories() {
    this.categoryService.getCategories().subscribe({
    next: (data:any) => {
      console.log("respusta",data);
      this.processCategoriesResponse(data);
    },
    error: (error:any) => {
      console.log("respusta", error)
      }
    })
  }

  processCategoriesResponse(resp: any) {
    const dataCategory: CategoryElement[] = [];
    if( resp.metadata[0].code == "00") {
      let listCategory = resp.categoryResponse.category;

      listCategory.forEach((element: CategoryElement) => {
        dataCategory.push(element);
      });

      this.dataSource = new MatTableDataSource<CategoryElement>(dataCategory);
    }
  }

  openCategoryDialog() {
    const dialogRef = this.dialog.open(NewCategoryComponent, {
      width: '450px',
    });

    dialogRef.afterClosed().subscribe((result:any) => {

    });
  }

}
export interface CategoryElement {
  description: string;
  id: number;
  name: string;
}
