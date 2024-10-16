import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from './models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductsService {
  private apiBaseUrl = 'http://localhost:8080/products';

  constructor(private http: HttpClient) {}

  listProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiBaseUrl);
  }

  getProduct(id: string): Observable<Product> {
    return this.http.get<Product>(`${this.apiBaseUrl}/${id}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiBaseUrl, product, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    });
  }

  updateProduct(id: string, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiBaseUrl}/${id}`, product);
  }

  deleteProduct(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiBaseUrl}/${id}`);
  }
}
