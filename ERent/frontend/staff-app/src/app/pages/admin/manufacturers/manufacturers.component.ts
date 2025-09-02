import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { ManufacturersService } from '../../../services/manufacturers.service';
import { DataTableComponent } from '../../../shared/data-table/data-table.component';
import { Manufacturer } from '../../../models/manufacturer.model';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-manufacturers',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatInputModule,
    FormsModule,
    DataTableComponent,
    MatIconModule
  ],
  templateUrl: './manufacturers.component.html',
  styleUrls: ['./manufacturers.component.css']
})
export class ManufacturersComponent implements OnInit {
  manufacturers: Manufacturer[] = [];
  filteredManufacturers: Manufacturer[] = [];

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
    });
  }

  applyFilter(ev: Event) {
    const value = (ev.target as HTMLInputElement).value.trim().toLowerCase();
    if (!value) {
      this.filteredManufacturers = [...this.manufacturers];
      return;
    }

    this.filteredManufacturers = this.manufacturers.filter(m =>
      m.name.toLowerCase().includes(value) ||
      m.country?.toLowerCase().includes(value) ||
      m.email?.toLowerCase().includes(value)
    );
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
    });
  }
}
