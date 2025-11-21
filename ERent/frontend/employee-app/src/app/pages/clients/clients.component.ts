import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTableComponent } from '../../shared/data-table/data-table.component';
import { MinimalPaginatorComponent } from '../../shared/minimal-paginator/minimal-paginator.component';
import { Client } from '../../models/client.model';
import { UserService } from '../../services/user.service';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [
    CommonModule,
    DataTableComponent,
    MinimalPaginatorComponent,
    MatIconModule,
    MatInputModule
  ],
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  allClients: Client[] = [];
  originalClients: Client[] = [];
  clients: Client[] = [];

  currentClientPage = 1;
  clientTotalPages = 1;

  pageSize = 10;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadClients();
  }

  loadClients() {
    this.userService.getClients().subscribe(data => {
      const list = data.map(c => ({
        ...c,
        fullName: `${c.firstName} ${c.lastName}`
      }));

      this.originalClients = [...list];   
      this.allClients = [...list];      

      this.setClientPage(1);
    });
  }

  setClientPage(page: number) {
    this.currentClientPage = page;
    const start = (page - 1) * this.pageSize;
    this.clients = this.allClients.slice(start, start + this.pageSize);
    this.clientTotalPages = Math.ceil(this.allClients.length / this.pageSize);
  }

  previousClientPage() {
    if (this.currentClientPage > 1) {
      this.setClientPage(this.currentClientPage - 1);
    }
  }

  nextClientPage() {
    if (this.currentClientPage < this.clientTotalPages) {
      this.setClientPage(this.currentClientPage + 1);
    }
  }

  blockUnblockClient(client: Client) {
    if (client.blocked) {
      this.userService.unblockClient(client.id).subscribe(() => this.loadClients());
    } else {
      this.userService.blockClient(client.id).subscribe(() => this.loadClients());
    }
  }

 applyFilter(event: Event) {
    const value = (event.target as HTMLInputElement).value.trim().toLowerCase();

    this.allClients = value
      ? this.originalClients.filter(c =>
          c.username.toLowerCase().includes(value) ||
          c.email.toLowerCase().includes(value) ||
          `${c.firstName} ${c.lastName}`.toLowerCase().includes(value)
        )
      : [...this.originalClients];

    this.setClientPage(1);
  }

  activateClient(client: Client) {
    if (!client.active) {
      this.userService.activateClient(client.id).subscribe(() => this.loadClients());
    }
  }
}
