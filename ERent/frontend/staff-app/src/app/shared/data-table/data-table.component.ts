import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MinimalPaginatorComponent } from '../minimal-paginator/minimal-paginator.component';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MinimalPaginatorComponent],
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent {
  @Input() dataSource: any[] = [];
  @Input() displayedColumns: string[] = [];
  @Input() columnLabels: { [key: string]: string } = {};
  @Input() currentPage = 1;
  @Input() totalPages = 1;

  @Output() edit = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
  @Output() previous = new EventEmitter<void>();
  @Output() next = new EventEmitter<void>();

  get generatedColumns(): string[] {
    return [...this.displayedColumns, 'actions'];
  }
}
