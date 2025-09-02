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

@Component({
  selector: 'app-manufacturers',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    DataTableComponent,
    MatIconModule,
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

  newManufacturer: Manufacturer = { 
    id: 0, 
    name: '', 
    country: '', 
    address: '', 
    phone: '', 
    fax: '', 
    email: '' 
  };

  editing: Manufacturer | null = null;

  constructor(private manufacturersService: ManufacturersService) {}

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
    if (!value) {
      this.filteredManufacturers = [...this.manufacturers];
    } else {
      this.filteredManufacturers = this.manufacturers.filter(m =>
        m.name.toLowerCase().includes(value) ||
        m.country?.toLowerCase().includes(value) ||
        m.email?.toLowerCase().includes(value)
      );
    }
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

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  startCreate() {
    console.log('Start create manufacturer');
  }

  addManufacturer() {
    this.manufacturersService.create(this.newManufacturer).subscribe(() => {
      this.newManufacturer = { id: 0, name: '', country: '', address: '', phone: '', fax: '', email: '' };
      this.loadManufacturers();
    });
  }

  editManufacturer(m: Manufacturer) {
    this.editing = { ...m };
  }

  updateManufacturer() {
    if (!this.editing) return;
    this.manufacturersService.update(this.editing.id, this.editing).subscribe(() => {
      this.editing = null;
      this.loadManufacturers();
    });
  }

  deleteManufacturer(id: number) {
    this.manufacturersService.delete(id).subscribe(() => {
      this.manufacturers = this.manufacturers.filter(m => m.id !== id);
      this.filteredManufacturers = this.filteredManufacturers.filter(m => m.id !== id);
      this.updatePagination();
    });
  }
}
