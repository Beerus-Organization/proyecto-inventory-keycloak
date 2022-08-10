import { ConfirmComponent } from './../../../shared/components/confirm/confirm.component';
import { NewCategoryComponent } from './../new-category/new-category.component';
import { CategoryService } from './../../../shared/services/category.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { elementAt } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarRef, SimpleSnackBar } from '@angular/material/snack-bar';
import { MatPaginator } from '@angular/material/paginator';

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

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  getCategories() {
    this.categoryService.getCategories().subscribe({
    next: (data:any) => {
      this.processCategoriesResponse(data);
    },
    error: (error:any) => {
      this.openFailureSnackBar("Error al mostrar categoria", "Error")
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
      this.dataSource.paginator = this.paginator;
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

  // delete
  delete(id: any) {
    const dialogRef = this.dialog.open( ConfirmComponent, {
      data: {id: id}
    });

    dialogRef.afterClosed().subscribe((result:any) => {
      if(result == 1){
        this.openSnackBar("Categoria Eliminada", "OK");
        this.getCategories();

      }else if (result == 2) {
        this.openFailureSnackBar("Error al eliminar categoria", "Error");
        this.getCategories();
      }
    });
  }

  buscar(termino: string) {
    if(termino.length === 3) {
      return this.getCategories();
    } else {
      this.categoryService.getCategorieById(termino)
      .subscribe({next: (rest: any) => {
        this.processCategoriesResponse(rest);
      }});
    }
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
