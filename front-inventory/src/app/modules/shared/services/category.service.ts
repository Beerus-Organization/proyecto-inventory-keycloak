import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http: HttpClient) { }

  /**
     get All categories
   */
  getCategories() {
    const endpoint = `${base_url}/category/categories`;
    return this.http.get(endpoint);
  }

  /*
   save the categories
  */
  saveCategories(body: any) {
    const endpoint = `${base_url}/category/categories`;
    return this.http.post(endpoint, body);
  }

  /**
   *  Update categorie
   */
  updateCategorie(body: any, id: any) {
    const endpoint = `${base_url}/category/categories/${id}`;
    return this.http.put(endpoint, body);
  }
}
