import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from './../../../shared/services/category.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-category',
  templateUrl: './new-category.component.html',
  styleUrls: ['./new-category.component.css']
})
export class NewCategoryComponent implements OnInit {

  public categoryForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private categoryService: CategoryService,
              private dialogRef: MatDialogRef<NewCategoryComponent>) {

      this.categoryForm = this.formBuilder.group( {
        name: ['', Validators.required],
        description: ['', Validators.required]
      });
   }

   onSave() {
    let data = {
      name: this.categoryForm.get('name')?.value,
      description: this.categoryForm.get('description')?.value
    }

    this.categoryService.saveCategories(data)
    .subscribe({
    next: (data: any) => {
      this.dialogRef.close(1);
      console.log(data);
    },
    error: (error: any) => {
      this.dialogRef.close(2);
      console.log(error);
      }
    })
  }

   onCancel() {
    this.dialogRef.close(3);
   }

  ngOnInit(): void {
  }

}
