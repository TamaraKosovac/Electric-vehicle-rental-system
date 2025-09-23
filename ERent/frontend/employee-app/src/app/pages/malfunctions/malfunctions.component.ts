import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';

import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { MinimalPaginatorComponent } from '../../shared/minimal-paginator/minimal-paginator.component';
import { MalfunctionsService } from '../../services/malfunctions.service';
import { Malfunction } from '../../models/malfunction.model';
import { MalfunctionFormComponent } from '../malfunctions/malfunction-form/malfunction-form.component';

@Component({
  selector: 'app-malfunctions',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    DataTableComponent,
    MinimalPaginatorComponent
  ],
  templateUrl: './malfunctions.component.html',
  styleUrls: ['./malfunctions.component.css']
})
export class MalfunctionsComponent implements OnInit {
  malfunctions: Malfunction[] = [];
  allMalfunctions: Malfunction[] = [];

  pageSize = 10;
  currentPage = 1;
  totalPages = 1;

  constructor(
    private malfunctionsService: MalfunctionsService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadMalfunctions();
  }

  loadMalfunctions() {
    this.malfunctionsService.getAll().subscribe(m => {
      this.allMalfunctions = m || [];
      this.malfunctions = [...this.allMalfunctions];
      this.updatePage();
    });
  }

  private updatePage() {
    this.totalPages = Math.ceil(this.malfunctions.length / this.pageSize) || 1;
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePage();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePage();
    }
  }

  onSearch(event: KeyboardEvent) {
    const value = (event.target as HTMLInputElement).value.toLowerCase().trim();
    this.malfunctions = this.allMalfunctions.filter(m =>
      m.description?.toLowerCase().includes(value) ||
      m.manufacturerName?.toLowerCase().includes(value) ||
      m.vehicleModel?.toLowerCase().includes(value)
    );
    this.currentPage = 1;
    this.updatePage();
  }

  openAddMalfunctionDialog() {
    const dialogRef = this.dialog.open(MalfunctionFormComponent, {
      width: '500px',
      data: {}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.malfunctionsService.create(result).subscribe({
          next: newM => {
            this.allMalfunctions.push(newM);
            this.malfunctions = [...this.allMalfunctions];
            this.updatePage();
            this.snackBar.open('Malfunction added successfully!', '', { 
            duration: 3000,
            horizontalPosition: 'right', 
            verticalPosition: 'top',     
            panelClass: ['snackbar-success']
          });
          },
          error: () => {
            this.snackBar.open('Failed to add malfunction.', '', { 
            duration: 3000,
            horizontalPosition: 'right',
            verticalPosition: 'top',
            panelClass: ['snackbar-error']
          });
          }
        });
      }
    });
  }
}
