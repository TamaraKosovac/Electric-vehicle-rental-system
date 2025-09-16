import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent {
  @Input() dataSource: any[] | MatTableDataSource<any> = [];
  @Input() displayedColumns: string[] = [];
  @Input() columnLabels: { [key: string]: string } = {};
  @Input() currentPage = 1;
  @Input() totalPages = 1;

  @Input() showInfo = true;
  @Input() showEdit = true;
  @Input() showDelete = true;
  @Input() showBlockUnblock = false;
  @Input() showActivate = false;                
  @Output() activate = new EventEmitter<any>(); 
  @Output() blockUnblock = new EventEmitter<any>();

  @Output() info = new EventEmitter<any>();  
  @Output() edit = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();
  @Output() previous = new EventEmitter<void>();
  @Output() next = new EventEmitter<void>();

  get generatedColumns(): string[] {
    return [...this.displayedColumns, 'actions'];
  }
}
