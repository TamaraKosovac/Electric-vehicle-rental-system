import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { ManufacturersService } from '../../../services/manufacturers.service';

@Component({
  selector: 'app-manufacturers',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatInputModule, FormsModule],
  templateUrl: './manufacturers.component.html',
  styleUrls: ['./manufacturers.component.css']
})
export class ManufacturersComponent implements OnInit {
  manufacturers: any[] = [];
  displayedColumns: string[] = ['id', 'name', 'country', 'address', 'contact', 'actions'];

  newManufacturer: any = { name: '', country: '', address: '', contact: '' };
  editing: any = null;

  constructor(private manufacturersService: ManufacturersService) {}

  ngOnInit() {
    this.loadManufacturers();
  }

  loadManufacturers() {
    this.manufacturersService.getAll().subscribe(data => this.manufacturers = data);
  }

  addManufacturer() {
    this.manufacturersService.create(this.newManufacturer).subscribe(() => {
      this.newManufacturer = { name: '', country: '', address: '', contact: '' };
      this.loadManufacturers();
    });
  }

  editManufacturer(m: any) {
    this.editing = { ...m };
  }

  updateManufacturer() {
    this.manufacturersService.update(this.editing.id, this.editing).subscribe(() => {
      this.editing = null;
      this.loadManufacturers();
    });
  }

  deleteManufacturer(id: number) {
    this.manufacturersService.delete(id).subscribe(() => {
      this.manufacturers = this.manufacturers.filter(m => m.id !== id);
    });
  }
}
