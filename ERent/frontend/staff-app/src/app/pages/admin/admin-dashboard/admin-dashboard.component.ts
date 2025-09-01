import { Component, OnDestroy, inject } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd, RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ConfirmLogoutDialogComponent } from '../../../shared/confirm-logout-dialog/confirm-logout-dialog.component';
import { Subject, filter, map, takeUntil } from 'rxjs';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, MatIconModule, MatDialogModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnDestroy {
  private dialog = inject(MatDialog);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private destroy$ = new Subject<void>();

  pageTitle = 'Admin dashboard';

  constructor() {
    this.router.events
      .pipe(
        filter(e => e instanceof NavigationEnd),
        map(() => {
          let child = this.route.firstChild;
          while (child?.firstChild) child = child.firstChild;
          const childTitle = child?.snapshot.data?.['title'];
          return childTitle ? `Admin dashboard - ${childTitle}` : 'Admin dashboard';
        }),
        takeUntil(this.destroy$)
      )
      .subscribe(title => (this.pageTitle = title));
  }

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

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
