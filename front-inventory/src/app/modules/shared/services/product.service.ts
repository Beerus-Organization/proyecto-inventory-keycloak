import { HttpClient } from '@angular/common/http';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';

const base_url = environment.base_url;

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }

  /**
   *  get All products
   */
  getProducts() {
    const endpoint = `${base_url}/product/products`;
    return this.http.get(endpoint);
  }
}
