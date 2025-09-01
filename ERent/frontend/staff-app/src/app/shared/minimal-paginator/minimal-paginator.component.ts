import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-minimal-paginator',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './minimal-paginator.component.html',
  styleUrls: ['./minimal-paginator.component.css']
})
export class MinimalPaginatorComponent {
  @Input() currentPage = 1;
  @Input() totalPages = 1;

  @Output() previous = new EventEmitter<void>();
  @Output() next = new EventEmitter<void>();
}
