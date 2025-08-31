import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ConfirmLogoutDialogComponent } from '../../../shared/confirm-logout-dialog/confirm-logout-dialog.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, MatIconModule, MatDialogModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  private dialog = inject(MatDialog);
  private router = inject(Router);

  openLogoutDialog(ev?: Event) {
    ev?.preventDefault();

    const ref = this.dialog.open(ConfirmLogoutDialogComponent, {
      autoFocus: false,
      restoreFocus: false,
      disableClose: true,           
      panelClass: 'logout-dialog'   
    });

    ref.afterClosed().subscribe(ok => {
      if (ok) {
        localStorage.removeItem('user');
        this.router.navigate(['/login']);
      }
    });
  }
}
