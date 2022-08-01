import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-new-category',
  templateUrl: './new-category.component.html',
  styleUrls: ['./new-category.component.css']
})
export class NewCategoryComponent implements OnInit {

  public categoryForm: FormGroup;
  constructor(private formBuilder: FormBuilder) {

      this.categoryForm = this.formBuilder.group( {
        name: ['', Validators.required],
        description: ['', Validators.required]
      });
   }

  ngOnInit(): void {
  }

}
