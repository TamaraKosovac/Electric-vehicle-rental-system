import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { ManufacturersService } from '../../../services/manufacturers.service';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';
import { Manufacturer } from '../../../models/manufacturer.model';
import { MatIconModule } from '@angular/material/icon';
import { MinimalPaginatorComponent } from '../../../shared/minimal-paginator/minimal-paginator.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ManufacturerFormComponent } from './manufacturer-form/manufacturer-form.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manufacturers',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatDialogModule,
    MatSnackBarModule,
    DataTableComponent,
    MinimalPaginatorComponent
  ],
  templateUrl: './manufacturers.component.html',
  styleUrls: ['./manufacturers.component.css']
})
export class ManufacturersComponent implements OnInit {
  manufacturers: Manufacturer[] = [];
  filteredManufacturers: Manufacturer[] = [];

  currentPage = 1;
  totalPages = 1;
  pageSize = 10;

  constructor(
    private manufacturersService: ManufacturersService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadManufacturers();
  }

  loadManufacturers() {
    this.manufacturersService.getAll().subscribe(data => {
      this.manufacturers = data;
      this.filteredManufacturers = [...data];
      this.updatePagination();
    });
  }

  applyFilter(ev: Event) {
    const value = (ev.target as HTMLInputElement).value.trim().toLowerCase();
    this.filteredManufacturers = !value
      ? [...this.manufacturers]
      : this.manufacturers.filter(m =>
          m.name.toLowerCase().includes(value) ||
          m.country?.toLowerCase().includes(value) ||
          m.email?.toLowerCase().includes(value)
        );
    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredManufacturers.length / this.pageSize) || 1;
    if (this.currentPage > this.totalPages) this.currentPage = this.totalPages;
  }

  get paginatedManufacturers() {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filteredManufacturers.slice(start, start + this.pageSize);
  }

  previousPage() {
    if (this.currentPage > 1) this.currentPage--;
  }

  nextPage() {
    if (this.currentPage < this.totalPages) this.currentPage++;
  }

  startCreate() {
    const dialogRef = this.dialog.open(ManufacturerFormComponent, { width: '600px' });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const { id, ...payload } = result;
        this.manufacturersService.create(payload as Manufacturer).subscribe({
          next: () => {
            this.loadManufacturers();
            this.snackBar.open('Manufacturer created successfully!', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open('Failed to create manufacturer.', '', {
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

  editManufacturer(m: Manufacturer) {
    const dialogRef = this.dialog.open(ManufacturerFormComponent, {
      width: '600px',
      data: { manufacturer: m }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.manufacturersService.update(m.id, result).subscribe({
          next: () => {
            this.loadManufacturers();
            this.snackBar.open('Manufacturer updated successfully!', '', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top',
              panelClass: ['snackbar-success']
            });
          },
          error: () => {
            this.snackBar.open('Failed to update manufacturer.', '', {
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

  deleteManufacturer(id: number) {
    this.manufacturersService.delete(id).subscribe({
      next: () => {
        this.loadManufacturers();
        this.snackBar.open('Manufacturer deleted successfully!', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-success']
        });
      },
      error: () => {
        this.snackBar.open('Failed to delete manufacturer.', '', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
          panelClass: ['snackbar-error']
        });
      }
    });
  }
}
