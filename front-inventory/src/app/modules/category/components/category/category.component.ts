import { NewCategoryComponent } from './../new-category/new-category.component';
import { CategoryService } from './../../../shared/services/category.service';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { elementAt } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  constructor(private categoryService: CategoryService,
              public dialog: MatDialog, private snackBar: MatSnackBar) { }

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

      if(result == 1){
        this.openSnackBar("Categoria Agregada", "OK");
        this.getCategories();

      }else if (result == 2) {
        this.openFailureSnackBar("Error al guardar categoria", "Error");
        this.getCategories();
      }
    });
  }

  edit(id:number, name: string, description: string) {
    const dialogRef = this.dialog.open(NewCategoryComponent, {
      width: '450px',
      data: {id: id, name: name, description: description}
    });

    dialogRef.afterClosed().subscribe((result:any) => {

      if(result == 1){
        this.openSnackBar("Categoria actualizada", "OK");
        this.getCategories();

      }else if (result == 2) {
        this.openFailureSnackBar("Error al actualizar categoria", "Error");
        this.getCategories();
      }
    });
  }

  private openSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar>{
  return this.snackBar.open(message, action, {
    duration: 3000,
    panelClass: ['green-snackbar', 'login-snackbar'],
   });
  }
  //Snackbar that opens with failure background
  private openFailureSnackBar(message: string, action: string): MatSnackBarRef<SimpleSnackBar>{
  return this.snackBar.open(message, action, {
      duration: 3000,
      panelClass: ['red-snackbar', 'login-snackbar'],
    });
   }
  }

export interface CategoryElement {
  description: string;
  id: number;
  name: string;
}
