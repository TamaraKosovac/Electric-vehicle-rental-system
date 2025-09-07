import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { MinimalPaginatorComponent } from '../../shared/minimal-paginator/minimal-paginator.component';

import { RentalsService } from '../../services/rentals.service';
import { RentalDetails } from '../../models/rental-details.model';

@Component({
  selector: 'app-rentals',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatSnackBarModule,
    DataTableComponent,
    MinimalPaginatorComponent
  ],
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.css']
})
export class RentalsComponent implements OnInit {
  rentals: RentalDetails[] = [];
  filteredRentals: RentalDetails[] = [];

  currentPage = 1;
  totalPages = 1;
  pageSize = 10;

  constructor(private rentalsService: RentalsService, private snackBar: MatSnackBar) {}

  ngOnInit() {
    this.loadRentals();
  }

  loadRentals() {
    this.rentalsService.getAll().subscribe({
      next: (data) => {
        this.rentals = data.map(r => ({
          ...r,
          client: `${r.clientFirstName} ${r.clientLastName}`
        }));
        this.filteredRentals = [...this.rentals];
        this.updatePagination();
      },
      error: () => {
        this.snackBar.open('Failed to load rentals.', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        });
      }
    });
  }

  applyFilter(ev: Event) {
    const value = (ev.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredRentals = !value
      ? [...this.rentals]
      : this.rentals.filter(r =>
          r.client.toLowerCase().includes(value) ||
          r.manufacturerName.toLowerCase().includes(value) ||
          r.vehicleModel.toLowerCase().includes(value)
        );
    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredRentals.length / this.pageSize) || 1;
    if (this.currentPage > this.totalPages) this.currentPage = this.totalPages;
  }

  get paginatedRentals() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredRentals.slice(start, start + this.pageSize);
  }

  previousPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }
}
