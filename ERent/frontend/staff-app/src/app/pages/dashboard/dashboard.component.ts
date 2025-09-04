import { Component, OnDestroy, inject } from '@angular/core';
import {
  Router,
  ActivatedRoute,
  NavigationEnd,
  RouterOutlet,
  RouterLink,
  RouterLinkActive
} from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ConfirmLogoutDialogComponent } from '../../shared/confirm-logout-dialog/confirm-logout-dialog.component';
import { Subject, filter, map, takeUntil } from 'rxjs';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnDestroy {
  private dialog = inject(MatDialog);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private destroy$ = new Subject<void>();
  private authService = inject(AuthService);

  pageTitle = 'Dashboard';

  userRole: string | null = null;

constructor() {
  const user = this.authService.getUser();
  this.userRole = user?.role?.toLowerCase() || null;

  const roleTitle =
    this.userRole === 'admin'
      ? 'Admin dashboard'
      : this.userRole === 'operator'
      ? 'Operator dashboard'
      : this.userRole === 'manager'
      ? 'Manager dashboard'
      : 'Dashboard';

  this.router.events
    .pipe(
      filter((e) => e instanceof NavigationEnd),
      map(() => {
        let child = this.route.firstChild;
        while (child?.firstChild) child = child.firstChild;
        const childTitle = child?.snapshot.data?.['title'];
        return childTitle ? `${roleTitle} - ${childTitle}` : roleTitle;
      }),
      takeUntil(this.destroy$)
    )
    .subscribe((title) => (this.pageTitle = title));
}

  openLogoutDialog(ev?: Event) {
    ev?.preventDefault();

    const ref = this.dialog.open(ConfirmLogoutDialogComponent, {
      autoFocus: false,
      restoreFocus: false,
      disableClose: true,
      panelClass: 'logout-dialog'
    });

    ref.afterClosed().subscribe((ok) => {
      if (ok) {
        this.authService.logout();
        this.router.navigate(['/login']);
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
