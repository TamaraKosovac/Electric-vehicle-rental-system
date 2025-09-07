import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { RentalPriceService } from '../../services/rental-price.service';
import { RentalPrice } from '../../models/rental-price.model';
import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { MinimalPaginatorComponent } from '../../shared/minimal-paginator/minimal-paginator.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RentalPriceFormComponent } from './rental-price-form/rental-price-form.component';


@Component({
  selector: 'app-rental-prices',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatSnackBarModule,
    DataTableComponent,
    MinimalPaginatorComponent
  ],
  templateUrl: './rental-prices.component.html',
  styleUrls: ['./rental-prices.component.css']
})
export class RentalPricesComponent implements OnInit {
  prices: RentalPrice[] = [];
  filteredPrices: RentalPrice[] = [];

  currentPage = 1;
  totalPages = 1;
  pageSize = 10;

  constructor(
    private rentalPriceService: RentalPriceService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadPrices();
  }

  loadPrices() {
    this.rentalPriceService.getAll().subscribe(data => {
      this.prices = data;
      this.filteredPrices = [...data];
      this.updatePagination();
    });
  }

  applyFilter(ev: Event) {
    const value = (ev.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredPrices = !value
      ? [...this.prices]
      : this.prices.filter(p =>
          p.vehicleType.toLowerCase().includes(value)
        );
    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredPrices.length / this.pageSize) || 1;
    if (this.currentPage > this.totalPages) this.currentPage = this.totalPages;
  }

  get paginatedPrices() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredPrices.slice(start, start + this.pageSize);
  }

  previousPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  editPrice(p: RentalPrice) {
    const dialogRef = this.dialog.open(RentalPriceFormComponent, {
      width: '400px',
      data: { rentalPrice: p }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.rentalPriceService.update(result.id, result).subscribe({
          next: () => {
            this.loadPrices();
            this.snackBar.open('Price updated successfully!', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open('Failed to update price.', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-error']
            });
          }
        });
      }
    });
  }
}
