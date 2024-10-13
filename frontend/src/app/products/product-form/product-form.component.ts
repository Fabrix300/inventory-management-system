import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../models/product';
import { ProductsService } from '../products.service';

@Component({
  selector: 'app-product-form',
  standalone: false,
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css',
})
export class ProductFormComponent {
  productForm: FormGroup;
  isEditMode: boolean = false;
  productId: string | null = null;

  constructor(
    private fb: FormBuilder,
    private productsService: ProductsService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: ['', Validators.required],
      quantity: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    console.log('ge');
    this.productId = this.route.snapshot.paramMap.get('id');
    if (this.productId) {
      this.isEditMode = true;
      this.productsService
        .getProduct(this.productId)
        .subscribe((product: Product) => {
          this.productForm.patchValue(product);
        });
    }
  }

  onSubmit(): void {
    console.log('ga');
    if (this.isEditMode && this.productId) {
      this.productsService
        .updateProduct(this.productId, this.productForm.value)
        .subscribe(() => {
          this.router.navigate(['']);
        });
    } else {
      this.productsService
        .createProduct(this.productForm.value)
        .subscribe((value) => {
          console.log(value);
          this.router.navigate(['']);
        });
    }
  }
}
